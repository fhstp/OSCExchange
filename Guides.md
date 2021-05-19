# OSCExchange Guides

This document contains some common situations and how to deal with them. Note
that some things were already explained in the [Tutorial](Tutorial.md) and will
not be repeated here.

## Sending multiple objects

You can use the `OSCArgs.multiple` method to create arguments from multiple
objects.

## Parsing

The `.receive` method can also use a `OSCArgsParser` to parse the incoming data
to a different object-type.

For example, say you have a custom class called `Instrument` which looks like
this:

```
public class Instrument {

    public final String name;
    
    public Instrument(String name)
    {
        this.name = name;
    }

}
```

If you want to convert an incoming `OSCArgs` object to an instance
of `Instrument` you need to create a custom parser.

_Note that you should also write a validator that checks if the args can be
converted to your type_

You can write a parser using a lambda expression:

```
OSCArgsParser<Instrument> instrumentParser =
    args -> {
        ...
    }
```

This method can do whatever you want it to, as long as it returns
an `Optional<Instrument>`. Return a value if the parsing was a success and empty
if it was not. In our case, the parser could look like this.

```
OSCArgsParser<Instrument> instrumentParser =
    args -> {
        Optional<String> name = OSCArgs.getArg(args, 0, String.class);
        
        if(name.isPresent())
            return Optional.of(new Instrument(name.get()));
        else
            return Optional.empty();
    }
```

If parsing fails (empty is returned), then the whole exchange fails with
an `OSCParsingError`.

If you choose to use parsing, the receive-listener that is passed to
the `receive` method also changes, to use an instance of your class instead
of `OSCArgs`. So in our case, this would be:

```.receive(address, instrumentParser, instrument -> { /* Do something */ });```