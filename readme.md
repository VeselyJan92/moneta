## Tools used
- Retrofit2
- OkHTTP
- Kotlin serialization
- Dagger / Hilt for Dependency injection
- Type safe navigation compose

## Features on the players screen
- Placeholders
- Paging
- Error handling 

## Architecture decisions 
Loading data on each screen. Reason: There were no additional requirements on data caching and performance. 
It helps with separation of concerns and easier mocking and debugging. 
Usually (In my experience) on Android projects The APIs really get highly coupled with screen and are hard to refactor