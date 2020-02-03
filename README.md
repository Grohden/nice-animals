# Nice Animals

[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

![Nice icon image](app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png)

## Description

Nice animals is a **simple android app** that shows some pictures of nice 
animals :D

It uses [shibe.online](http://shibe.online) as a source for those pictures

## Why this?

This was a challenge to get a job, the first implementation was using java 8 + streams and
completable futures, after some time I switched to kotlin and rx, replaced the streams with
sequences (or simply decent function calls) and completable futures with observables. 
Then recently I migrated it to use android jetpack stuff (room, livedata, etc.)

I really like this app, and I'm experimenting things on it, it is still simple.
Also, i made a version of this app using flutter [here](https://github.com/Grohden/nice-animals-flutter)

## TODO

* [ ] Shared view transitions between grid and fullscreen view
* [ ] Add favorite button
    * [ ] Add a bottom navigation with the favorite section
* [ ] Download image to local gallery
* [ ] Option to set image as background to launcher & lockscreen 
