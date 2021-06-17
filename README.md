# OSCExchange

This a Kotlin library for easily getting started with OSC in your Android
project.

First define the devices addresses

```val pair = OSCDevicePair(localAddress, remoteAddress)```

Next plan the exchange you want to have between the devices

```
val exchange = OSCExchange.new()
                    .add(SendRequest.new(sendAddress)
                            .withArgs(OSCArgs.single("Hello"))
                            .onError { error -> /* Handle error */ })
                    .build()
```

Finally, run the exchange asynchronously between your devices

```

exchange.tryMakeRunnable(devicePair)
    .onSuccess { it.run() }
    .onFailure { /* Handle failure */ }

```

## Getting started

[Learn how to use OSCExchange in your project](Tutorial.md)  
[Read the guides](Guides.md)  
[Check out the changelog](CHANGELOG.md)

## Prerequisites

This project targets SDK-level 24.