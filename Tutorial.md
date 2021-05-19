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
OSCAddress pingA = OSCAddress.create("/ping/A").get();
OSCAddress pingB = OSCAddress.create("/ping/B").get();
```

Note that the `OSCAddress.create` method returns an `Optional<OSCAddress>`.This
is because it first checks if the given string is a valid address, and if it is
not, it returns empty. We use `.get()` in this case, because we know that our
chosen addresses are valid, but usually you would want to check first if a value
is present.

### Building the exchange

We use the `OSCExchange.buildNew()` method to start building an exchange. We
then use the `send` and `receive` methods to create our request-chain. Finally,
we use the `onComplete` method to create our exchange. Don`t worry, everything
will be explained further down.

```
OSCExchange exchange = OSCExchange.buildNew()
                       .send(pingA, null)
                       .receive(pingB, null)
                       .onComplete(null);
```

Notice all the `nulls` in this request. We need to fill those in for it to work.
Lets go bit by bit.

#### Sending data

The first method we used is the `send` method, which says that the first step of
this exchange is to send some data to the remote. In our case, we would like to
send the number 1. Right now, we are sending `null`, so lets change that. To
send a single value, we can use the `OSCArgs.single` method.

Our new line should look like this:

```.send(pingA, OSCArgs.single(1))```

This will send the number 1 to the given address, and once its done, move on to
the next request.

#### Receiving data

To receive data, we use the `receive` method, which has multiple overloads
depending if you want to do validation and parsing. For this example, we are
only interested in validation, as we want to check, if we receive the correct
number.

Before we do that though, we need to replace that `null`. In this case it is the
listener for when a message is received. We can pass a lambda expression to
execute when data is received.

```.receive(pingB, args -> { /* Do something */ })```

Note, that the `args` object is another instance of `OSCArgs`. We will look into
how they can be interacted with later.

In our case, we want to print to the console if we receive data, so our handler
could look like this:

```.receive(pingB, args -> System.out.println("Success")```

The exchange will wait on this request until data was successfully received and
only then move on to the next request.

#### Adding validation

To make validate received messages, we must add a validator to our `receive`
method.

There are a few prebuilt validators, such as `OSCValidator.single` which checks
if only a single value was received. This seems fitting for us, since we expect
a single integer, so our validator would look like this.

```OSCValidator responseValidator = OSCValidator.isSingle();```

However, we also want to check if that value is an integer and specifically 2.
Using the `and` method, we can chain validators and we can use a lambda
expression to define our own validator. Let's do this now.

```
OSCValidator responseValidator = OSCValidator.isSingle()
                                             .and(args -> {
                                                Optional<Integer> i = OSCArgs.getArg(args, 0, Integer.class);                                      
                                                return i.isPresent() && i.get() == 2;
                                             });
```

In this method we use the `OSCArgs.getArg` method to get the first value as an
integer. Note that this returns an Option, in case the value is either not there
or not an integer. We then check, if the value is present and if it is equal to
2 or not.

We can finally pass this validator to our receive method.

```.receive(pingB, responseValidator, args -> System.out.println("Success"))```

Our validator will now be called once data is received. If the data passes the
validator, it will be forwarded to our receive-listener. If it does not pass,
an `OSCValidationError` occurs.

#### Handling errors

In order to handle any errors that occur during the exchange, we can add
an `onError` method call to our exchange. I like to add this call, before
the `onComplete` call.

```
...
.onError(null)
.onComplete(null);
```

Once again, we need to fill in the `null`. In this case, it expects an error
listener, which we can specify using a lambda expression.

```.onError(error -> { /* Handle error */ })```

The `error` object is an instance of `OSCError` or one of its subclasses. Its
like an exception, even though is does not inherit from it.

In our case we want to print "No success", so our line would look like this:

```.onError(error -> System.out.println("No success"))```

#### Completing the exchange

The final call is to `onComplete` and this method takes a listener, that will be
called once the whole exchanges completes without any errors. In our case, we do
not care about this callback, so we'll just leave it as `null`.

### Running the exchange

Our finished exchange should now look like this

```
OSCExchange exchange = OSCExchange.buildNew()
                       .send(pingA, OSCArgs.single(1))
                       .receive(pingB, responseValidator, args -> System.out.println("Success"))
                       .onError(error -> System.out.println("No success"))
                       .onComplete(null);
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

We can then use this pair to run our exchange. As this will of course use the
network, it is best to run the exchange on a background thread. You can do this
easily using:

```OSCExchange.runAsyncBetween(exchange, pair);```

And that's it. You exchange should now be running and your callbacks be called.
For more information, read the JavaDocs included in the project.