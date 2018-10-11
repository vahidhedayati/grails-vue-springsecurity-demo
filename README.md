

To run this application
```
./gradlew bootRun --parallel
``` 


This will launch both apps and access url via 

http://localhost:3000


[Video running through authentication](https://www.youtube.com/watch?v=dawm5DNSi9w)

[Video part 2 pagination](https://www.youtube.com/watch?v=WIi3x_5n03o)


You may also need to install `npm` locally. Whilst trying to start up the site as per above instructions.
I had to stop and run these two commands (during different intervals):

```
npm install --save uiv
npm install --save vue

```




-----


To create a grails 3  vue application run the following:  

```
grails create-app myapp --profile=vue

```




Taken from :
----
[Official guide can be found here ](http://guides.grails.org/building-a-vue-app/guide/index.html)  
```
git clone https://github.com/grails-guides/building-a-vue-app.git
```


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



