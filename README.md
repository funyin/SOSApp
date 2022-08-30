# SOS App
An SOS app built with Jetpack compose and clean architecture. UI inspiration from https://dribbble.com/shots/15007249-SOS-Alert

<div width="100%">
<img src="https://github.com/funyin/SOSApp/blob/master/assets/Screenshot_20220808_144330.png?raw=true" alt="SOS App Preview" width="300"  style="float: left;"/>
<img src="https://raw.githubusercontent.com/funyin/SOSApp/master/assets/Screenshot2.png" alt="SOS App Preview" width="300" style="float: right;"/>
</div>

## Data sources
- Local: ProtoDataStore
- Remote: Dummy REST API
- Image Capture: CameraX
- Location Service: Google Location Service

## Basic Features
-  Add and Remove emergency contact numbers
- Capture Image
- Record Location
- POST Details(Emergency contacts, Image, Location) to an API

## State flow
- onTap(send SOS)-> Check Camera Permission -> Capture Image ->Check Location Permission -> Record Location -> POST Details
