Taken from 


To create a grails vue application to do this in grails 3.3+ 

```
grails create-app myapp --profile=vue

```

[You tube video running through authentication](https://www.youtube.com/watch?v=Z0qr6XIG4Kc)



Official guide
----
[Official guide can be found here ](http://guides.grails.org/building-a-vue-app/guide/index.html)  
```
git clone https://github.com/grails-guides/building-a-vue-app.git
```


To run this application
```
./gradlew bootRun --parallel
``` 


This will launch both apps and access url via 

http://localhost:3000


The first thing to consider when setting up a vue app is to disable esLint.

In the `client/config/index.js` 

```
    // Use Eslint Loader?
    // If true, your code will be linted during bundling and
    // linting errors and warnings will be shown in the console.
    useEslint: false,
```

Otherwise it will complain about very specific spacing etc.

I have been working on a fairly simple edit/save function of a given specific vehicle. The process appears to be a little easier than using reactjs. Please review [this push](https://github.com/vahidhedayati/experiments/commit/5fefca620e7766012cb6d8a8e07bd348490bb673)



