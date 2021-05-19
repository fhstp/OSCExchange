# OSCExchange

This a library for easily getting started with OSC in your Android project.

First define the devices addresses

```OSCDevicePair pair = new OSCDevicePair(localAddress, remoteAddress);```

Next plan the exchange you want to have between the devices

```
OSCExchange exchange = OSCExchange.buildNew()
                                  .send(sendAddress, OSCArgs.single("Hello"))
                                  .receive(responseAddress, receivedArgs -> {
                                      // Handle response
                                  })
                                  .onError(error -> {
                                      // Handle error
                                  })
                                  .onComplete(() -> {
                                      // Handle exchange completion
                                  });
```

Finally, run the exchange asynchronously between your devices

```OSCExchange.runBetweenAsync(exchange, pair);```

## Getting started

[Learn how to use OSCExchange in your project](Tutorial.md)  
[Read the guides](Guides.md)  
[Check out the changelog](CHANGELOG.md)

## Prerequisites

This project targets SDK-level 24.