# OSCExchange Tutorial

In this tutorial you will learn how to execute a "Ping-exchange" between two
devices. This means that one device will send a signal to the other, which will
in turn respond with its own signal. This way, the devices can be sure that they
are both online and connected.

## Creating the exchange

OSCExchanges can be prepared at compile-time by the user. The exchange for this
tutorial will look like this:

```
1. A sends the number 1 to B
2. B receives the signal and sends back the number 2
3. A waits for a response by B and once it receives one, checks if it is the number 2 or something else
4.1 If the response was the number 2, print "Success"
4.2 If the response was any other number, print "No success"
```

### Creating the addresses

Before the exchange can be created, we need to define the addresses that will be
used for sending the messages. For this tutorial we will use `/ping/a` for
sending the first message and `/ping/b` for the response.

Lets create variables for these addresses.

```
OSCAddress pingA = OSCAddress.create("/ping/A");
OSCAddress pingB = OSCAddress.create("/ping/B");
```

Note that the `OSCAddress.create` method will throw an exception if the given
String is not a valid OSC-address.

### Building the exchange

We use the `OSCExchange.new()` method to start building an exchange. We then use
the `addRequest` method to create our request-chain. Finally, we use the `build`
method to create our exchange. Don`t worry, everything will be explained further
down.

```
val exchange = OSCExchange.new()
               .addRequest(SendRequest.new(pingA))
               .addRequest(ReceiveRequest.new(pingB))
               .build();
```

This is the absolute minimum exchange, but we can spice it up quite a bit to
make it more suited to our needs.

#### Sending data

The first request in our exchange is a `SendRequest` to the `pingA` address. In
our case, we would like to send the number 1. Right now, we are sending nothing,
so lets change that. To send a single value, we can use the `.withArgs()`
method. To create `OSCArgs`for a single value we can use `OSCArgs.single`

Our new line should look like this:

```
.addRequest(SendRequest.new(pingA)
                    .withArgs(OSCArgs.single(1))
    )
```

This will send the number 1 to the given address, and once its done, move on to
the next request.

#### Receiving data

To receive data, we use the `ReceiveRequest` class, which also supports
validation and parsing. For this example, we are only interested in validation,
as we want to check, if we receive the correct number.

Before we do that though, we should add a receive listener, so when can notified
when data was received and passed validation. We can do this by adding the
following line to our `ReceiveRequest` builder.

```.onReceive { args -> /* Handle success */ }```

Note, that the `args` object is another instance of `OSCArgs`. We will look into
how they can be interacted with later.

In our case, we want to print to the console if we receive data, so our handler
could look like this:

```.onReceive { args -> System.out.println("Success") }```

The exchange will wait on this request until data was successfully received and
only then move on to the next request.

#### Adding validation

To make validate received messages, we must add a validator to
our `ReceiveRequest` builder.

There are a few prebuilt validators, such as `ArgsValidators.hasSingleArg` which
checks if only a single value was received. This seems fitting for us, since we
expect a single integer, so our validator would look like this.

```val responseValidator = ArgValidators.hasSingleArg```

However, we also want to check if that value is an integer and specifically 2.
Using the `and` method, we can chain validators and we can use a lambda
expression to define our own validator. Let's do this now.

```

val responseValidator = ArgValidators.hasSingleArg
                                     .and { args ->
                                        val i = args.tryGetArgOfType<Int>(0)
                                        i.isPresent() && i.get() == 2
                                    }
```

In this method we use the `tryGetArgOfType` method to get the first value as an
integer. Note that this returns an Option, in case the value is either not there
or not an integer. We then check, if the value is present and if it is equal to
2 or not.

We can finally pass this validator to our `ReceiveRequest` builder.

```.withValidator(responseValidator)```

Our validator will now be called once data is received. If the data passes the
validator, it will be forwarded to our receive-listener. If it does not pass,
an `OSCValidationException` occurs.

#### Handling errors

In order to handle errors that occur during the exchange, we can add
an `onError` listener to any requests where we expect errors. So for example, to
check for errors in our `SendRequest` the could do something like this:

```
.addRequest(SendRequest.new(pingA)
                    .withArgs(OSCArgs.single(1))
                    .onError { error -> /* Handle error */ }
    )
```

The `error` object is an instance of `OSCException` or one of its subclasses.

In our case we want to print "No success", so our line would look like this:

```.onError { error -> System.out.println("No success") }```

#### Completing the exchange

The final call is to `build()` and this method will finally create
an `OSCExchange` object.

### Running the exchange

Our finished exchange should now look like this

```
val exchange = OSCExchange.new()
               .addRequest(SendRequest.new(pingA)
                    .withArgs(OSCArgs.single(1))
                    .onError { error -> System.out.println("No success") }
                )
               .addRequest(ReceiveRequest.new(pingB)
                    .withValidator(responseValidator)
                    .onReceive { args -> System.out.println("Success") }
               )
               .build();
```

To run our exchange, we first need to specify the addresses of the machines we
want the exchange to happen between.

First create the addresses using `InetSocketAddress` objects and then store them
in a `OSCDevicePair` object.

```
InetSocketAddress local = new InetSocketAddress(localIp, localPort);
InetSocketAddress remote = new InetSocketAddress(remoteIp, remotePort);

OSCDevicePair pair = new OSCDevicePair(local, remote);
```

We can then use this pair to run our exchange. For this we must first make our
exchange runnable, which means opening the specified ports. We can do this using
the following procedure:

```
exchange.tryMakeRunnable(pair)
    .onSuccess { }
    .onFailure { }
```

As you can see, the result of the `tryMakeRunnable` method is a `Result` object.
We can use it to handle both the success and failure cases when trying to open
the ports. If the ports were opened successfully we can then finally run the
exchange:

```.onSuccess { it.run() }```

Note that the `run` method is a coroutine, so make sure to handle it correctly.

And that's it. Your exchange will now be run. 