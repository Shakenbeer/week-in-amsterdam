# Android Boilerplate Project
Project for storing boilerplate code and playing with new Android features.

### Overview
This repo serves a few purposes. Firt of all, it represents an Android app architecture and structure. At least, what I'm considering the best approach at the moment. Second, it contains some common code, that I keep using from project to project. And third, this is a playground for examination of new things from the Android development world.

Week In Amsterdam - the application that pulls flights data from Skyscanner and shows it to the user. Nothing fancy, but that's what is our job about 90% of the time.

### Architecture
#### Structure
Every cool kid prefers feature-per-package, so do I. This project contains only one feature though, so I decided to simplify it a bit. Three main packages: `data`, `domain`, `presentation`.

#### Clean Architecture
I'm not going to torture you and show [_the diagram_][1] that you've probably seen a few times already. I'll try to explain it as simple as possible. All business logic goes to `domain` package. `presentation` interacts with logic through use cases (sometimes called interactors). `domain` knows nothing about other layers. That's why `FlightsRepo` interface is in `domain`. We are not going to violate _The Dependency Rule_.
It seems natural to put something with _repo_ in name to the data layer. But, and here is Uncle Bob speaking:
> Nothing in an inner circle can know anything at all about something in an outer circle. In particular, the name of something declared in an outer circle must not be mentioned by the code in the an inner circle.

Implementation of `FlightsRepo` is in `data`, though. But `domain` knows nothing about it.

#### Dependency injection
Dependency injection simplifies following clean architecture approach. Dagger 2 is a current choice for DI framework.

It's worth to mention, that I'm "starting" injection from the view model. Why? As you know, `ViewModel` should be provided by `ViewModelProvider`, and, to initialize injection in Activity(Fragment) one should use a custom factory. You've probably seen [this implementation][2] already. Personally, I found this code confusing and ugly, but it's only a matter of taste. So, I initialize injection in `ViewModel`. The only disadvantage is that I need `AndroidViewModel`.
```kotlin
class FlightsViewModel(application: Application) : AndroidViewModel(application) {
    init {
            (application as WiaApplication).component.inject(this)
            loadFlights()
    }
}    
```    
**//TODO:** replace Dagger with Koin and rid of `AndroidViewModel`.

#### Data
Again, it's a matter of taste - how you implement data layer. I'm going to roughly describe my approach.

In a perfect world of package-per-feature, every feature would have its own data layer (if it needs data, of course). In the real world of thin clients, you have one remote data source. If there are requirements, you could have a couple of local data sources. Thus, the domain's `repo` interface defines your needs, data package implements them.

#### Domain
This is where logic goes to. Most of the time you only pass data from repositories to screens here. If you need tricky filtering - add it here. If you need to adjust a user's input for a proper request - do it here. If you need to find the Answer to the Ultimate Question of Life, the Universe, and Everything - do it here. Just please, don't add any dependencies to `presentation` or `data`.

In the perfect world domain is framework-free and 3rd-party-libraries-free. Though, there could (and will) be exceptions.

#### Presentation
MVVM on Architecture components from Android. I believe this is the most convenient way to implement the presentation level of your app at the moment. Implementation details could vary: you can use Android Data Binding or avoid it, you can use LiveData, RxJava or something else for data propagation, Fragments or custom views. 

I'm going to briefly describe my approach.

`ViewModel` executes `UseCase` asynchronously. This is the place where I prefer to make operations (i/o mostly) async. At the moment I use RxJava for this. I'm aware of this overkill, so that's a room for another

**//TODO:** replace RxJava with coroutines.

On every user action, intermediate stage or final result `LiveData` emits `ViewState`. In other words, on everything, that should be presented to the user in some way, `LiveData` propagates `ViewState`. `ViewState` is a sealed class, which descendants represent one particular screen of a current feature. Perhaps code says more:
```kotlin
sealed class FlightsViewState
object NoInternetState : FlightsViewState()
object LoadingState : FlightsViewState()
class ErrorState(val throwable: Throwable): FlightsViewState()
object NoFlightsState : FlightsViewState()
class DisplayState(val itineraries: List<ItineraryView>): FlightsViewState()
```
Here you have everything you may need to show.

`View` (`Activity`) observes those states. Because of sealed class every state should be handled:
```kotlin
        flightsViewModel.flightsLiveData.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is NoInternetState -> showNoInternet()
                    is LoadingState -> showLoading()
                    is ErrorState -> showError(state.throwable)
                    is NoFlightsState -> showNoFlights()
                    is DisplayState -> showFlights(state.itineraries)
                }
            }
        })
```
How it looks:

| Loading | List | Empty result | No Internet | Server Error |
|---------|------|--------------|-------------|--------------|
|![Loading](https://github.com/Shakenbeer/week-in-amsterdam/blob/master/images/state_loading.gif)|![List](https://github.com/Shakenbeer/week-in-amsterdam/blob/master/images/state_list.png)|![Empty result](https://github.com/Shakenbeer/week-in-amsterdam/blob/master/images/state_empty_result.png)|![No Internet](https://github.com/Shakenbeer/week-in-amsterdam/blob/master/images/state_no_internet.png)|![Server error](https://github.com/Shakenbeer/week-in-amsterdam/blob/master/images/state_server_error.png)|

All credits for _sealed-class-state_ go to [Antoni Castejón García][3].

### Testing
### Unit tests
My professor of mathematical analysis used to say: _"Obvious things are the hardest things to prove"_. I could apply it to unit testing of Android applications, most of which are thin clients. "What I have to test if I only fetch data and present it to users?". I have said such thing a few times and I have heard it from colleagues. And I've never found any practical answers, only _"You should write tests because you should"_.  

So, here are some _real_ things you could apply right away:

1. Test exceptions. Can you get a server error (with code from 500)? Test, if you handle it on every level:
* Data
```kotlin
    @Test(expected = UnexpectedServerError::class)
    fun `if server error then throw unexpected error`() {
        val call = Calls.response(Response.error<String>(500,
                ResponseBody.create(MediaType.parse("text/plain"), "Unexpected server error response")))
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        `when`(flightsService.createSession(request.country, request.currency, request.locale, request.originPlace,
            request.destinationPlace, request.cabinClass, request.outboundDate, request.inboundDate)).thenReturn(call)
        remoteFlightsSource.topFlights(request)
    }
```    
* Repository
```kotlin
    @Test(expected = UnexpectedServerError::class)
    fun `if source throws unexpected error then throw unexpected error`() {
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        doAnswer { throw UnexpectedServerError() }.whenever(flightsSource).topFlights(request)
        remoteFlightsRepo.getTopFlights(request)
    }
```
* Domain
```kotlin
    @Test(expected = UnexpectedServerError::class)
    fun `if repo throws unexpected error then throw unexpected error`() {
        doAnswer { throw UnexpectedServerError() }.whenever(flightsRepo).getTopFlights(any())
        getNextWeekFlightsUseCase.execute()
    }
```
* Presentation
```kotlin
    @Test
    fun `if unexpected server error then emit error state`() {
        doAnswer { throw UnexpectedServerError() }.whenever(getNextWeekFlightsUseCase).execute()
        flightsViewModel.obtainFlights()
        assert(flightsViewModel.flightsLiveData.value is ErrorState)
    }
```
2. Does the server has well-defined error states? (For example, if your request was built poorly). Test this case in the same way, on every level.
3. You have to test handling of emtpy result at least on presentation level:
```kotlin
    @Test
    fun `if no flights then emit no fligts state`() {
        `when`(getNextWeekFlightsUseCase.execute()).thenReturn(emptyList())
        flightsViewModel.obtainFlights()
        assert(flightsViewModel.flightsLiveData.value is NoFlightsState)
    }
```
4. Don't forget to check your handling of the absence of the Internet:
```kotlin
    @Test
    fun `if no internet then show no internet` () {
        flightsViewModel.connectivity = object : Connectivity {
            override fun isConnectedToInternet() = false
        }
        flightsViewModel.loadFlights()
        assert(flightsViewModel.flightsLiveData.value is NoInternetState)
    }
```
5. Finally, test your data. To be more specific, test mapping between levels. Such tests are huge and boring.

Generally speaking, items 1-4 are about testing edge cases, 5 is about data. And after you get more logic, more cases - you already know, where to start.

One last thing to say. Writing tests is really boring. If you do everything right in the first try - you will never see your tests again and you won't be able to appreciate them. If you don't write tests, then you won't appreciate them either. From my experience, that only time, when you _do appreciate tests_ is when _new requirements force you to make changes in 75% of the project's source files, and your 500+ unit tests save your ass from manual regression testing of every feature in the project_.

So far that only had happened a couple of times for me. I can only imagine, how much time and money were lost on regressions on those projects, that were not properly covered with unit tests.

#### Integration tests
Those are rear beasts... Or no? Imagine, you want to check the mapping between domain and presentation. You have a predefined list of domain models, apply the mapping, get a list of presentation models. And you check if corresponding fields contain proper values. But initialization of the list of domain models it boring and time-consuming. You _must_ create and fill every instance by hand. So, you go lazy and use the JSON with server response, you parse it and map server models to the domain list with another mapping from your project. And that's how you get an integration test:
```kotlin
    //TODO this is integration test because of FlightsResponseMapper.responseToFlights
    @Test
    fun `verify conversion of a flight`() {
        val flights = responseToFlights(responseFromFile("proper_1st_page_results.json")) //<--- another mapping
        `when`(getNextWeekFlightsUseCase.execute()).thenReturn(flights)
        flightsViewModel.obtainFlights()
        //...
    }
```

#### UI tests
**//TODO:** add ui tests to the project.


[1]: http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
[2]: https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.kt
[3]: https://proandroiddev.com/android-clean-architecture-with-viewmodel-usecases-and-repositories-part-1-b9e63889a1aa
