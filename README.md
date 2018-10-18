# Nice Animals

![Nice icon image](app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png)

## Description

Nice animals is a **simple android app** that shows some pictures of nice 
animals :D

It uses [shibe.online](http://shibe.online) as a source for those pictures

## Why this?

This was a challenge to get a job, the first implementation was using java 8 + streams and
completable futures, after some time i switched to kotlin and rx, replaced the streams with
sequences (or simply decent function calls) and completable futures with observables.

I really like this app, and i'm experimenting things on it, it is still simple.
Also, i made a version of this app using flutter [here](https://github.com/Grohden/nice-animals-flutter)

## Dependencies

As a guide to myself and some beginner, i'm listing the libs i used and some comments about them

 * OkHttp - To configure retrofit with logs
 * Retrofit - To map your endpoint interfaces and make them typesafe
 * Koins - For DI, i was using Dagger 2, which in my opinion is too complex, koins do the same in a more
 simple and concise syntax.
 * Timber - A better logger than the android default one
 * RxJava, RxBindings, RxAndroid - The Rx libraries to make a reactive app
 * Picasso - To fetch images easily, with caches management
 * PhotoVIew - To make a zoomable image
 * Realm - The DB layer, but i don't recommend realm these days, zero copy is nice but the price to pay is TOO HIGH:
   * It doesn't support decent queries - and string comparision
   * It doesn't support properly relations
   * It is **really** easy to break at runtime
   * It doesn't supports enums because they don't exist in JS (???confuse guy???)
   * It doesn't play well with kotlin models
   * Room seems to do a better work - i will use room in the near future.
   * You should use realm fields plugin, but no one tells you that


### Dependencies to use in the future

 * Room
 * AutoValue
 * Blade
 * Anvil