# OSCExchange Guides

This document contains some common situations and how to deal with them. Note
that some things were already explained in the [Tutorial](Tutorial.md) and will
not be repeated here.

## Sending multiple objects

You can use the `OSCArgs.multiple` method to create arguments from multiple
objects. You can also use `OSCArgs.fromList` to achieve the same thing from a
list.

## Parsing

The builder for `ReceiveRequests` can also use parsing to convert the
received `OSCArgs` to some other type.

For example, say you have a custom class called `Instrument` which looks like
this:

```
public data class Instrument(val name: String)
```

If you want to convert an incoming `OSCArgs` object to an instance
of `Instrument` you need to create a custom parser.

_Note that you should also write a validator that checks if the args can be
converted to your type_

You can write a parser using a lambda expression:

```
val instrumentParser =
    args -> {
        ...
    }
```

This method can do whatever you want it to, as long as it returns
an `Instrument?`. Return a value if the parsing was a success and null if it was
not. In our case, the parser could look like this.

```
val instrumentParser =
    args -> {
        val name = args.tryGetArgOfType<String>(0)
        
        if(name.isPresent()) Instrument(name.get())
        else null
    }
```

If parsing fails (null is returned), then the request fails with
an `OSCParsingException`.

If you choose to use parsing, you should also pass a method to receive the newly
parsed object, like this:

```
ReceiveRequest.new(address)
              .withParser(instrumentParser, { instrument -> })
```

## Timeouts

Most of the time, you don't want to wait for an answer from a remote forever.
This is why all `ReceiveRequests` have a standard timeout of 5000 milli-seconds,
after which a `OSCTimeoutException` is sent to the requests `onError` handler.

The timeout time can be changed by adding a `withTimout` call to the request
builder like this:

```
ReceiveRequest.new(address)
              .withTimeout(1000)
```

In this case, the timeout was shortened to 1000 milli-seconds.

The timeout can also be disabled by calling `withoutTimeout` instead:

```
ReceiveRequest.new(address)
              .withoutTimeout()
```