# SOS App
An SOS app built with Jetpack compose and clean architecture.
UI inspiration from https://dribbble.com/shots/15007249-SOS-Alert
## Basic Features
-  Add and Remove emergency contact numbers
- Capture Image
- Record Location
- POST Details(Emergency contacts, Image, Location) to an API

## State flow
- onTap(send SOS)-> Check Camera Permission -> Capture Image ->Check Location Permission -> Record Location -> POST Details