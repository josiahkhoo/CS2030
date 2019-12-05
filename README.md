# CS2030
AY19/20 Semester 1 CS2030 Programming Methodology II (Hopefully you will find this summary useful)

# Chapter 1 - Programming Paradigms
Imperative (procedural)
- Specifies how computation proceeds e.g. using if else conditions and for loops
Object-Oriented
- Self explanatory
- Using different classes
Declarative
- Specifies what should be computed, rather than compute it
Functional
- This is a form of declarative programming

Primitive data-types
- The most basic data types available within the Java language, serves as the building blocks of data manipulation in Java
- There are 8 that are available:
    - Numeric primitives: short, int, long, float and double
    - Textual primitives: byte and char
    - Boolean and null primitives: boolean and null

String Manipulation
- You can use `StringBuilder` API to modify `String`s
- `StringBuilder()` behaves as a constructor with no characters in it an initial capacity of 16 characters.
- `StringBuilder(String str)` constructs a string builder initialized to the contents of the specified string.
    - `.append(almost anything)` adds almost anything to the end of the sequences.
    - `.indexOf(String str)` returns the index within this string of the first occurence of the specified substring in `int`
    - `.indexOf(String str, int fromIndex)` same as the above, but you can decide from where
    - `.substring(int start, in end)` allows for string splicing (start inclusive, end exclusive) but returns a `String`
    - `.length()` returns an `int` of the character count
    - `.toString()` returns a `String` version
- `.toCharArray()` converts a string to a new character array
- `.toLowerCase()`
- `.toUpperCase()`
- `String(String str)` makes a copy of a current string

`Collection`
- An interface in which all data structures can operate through
- Provides fixed and common set of methods that all data structures can use
    - `.contains(Object o)` returns a `boolean`
    - `.add(E e)` returns a `boolean` and adds e to the collection
    - `.clear()` returns `void` and clears the collection
    - `.isEmpty()` returns `true` if collection contains no elements
    - `.iterator()` creates something like a scanner where you can do `.hasNext()`
    - `.size()` returns an `int` of the number of elements in the collection
    - `.stream()` returns a sequential `Stream` with the collection as its source
    - `.toArray()` returns an array containing all of the elements in this collection
- `Collections.sort(List<T> list, Comparator<? super T> c)` sorts the specified list according to the order induced by the comparator (works without comparator to sort by natural ordering)
- `Collections.max(Collection<? extends T> coll)` returns the maximum element, min exists too
- `Collections.reverse(List<?> list)` is self-explanatory

`Arrays`
- `Arrays.asList(T... a)` converts an array to a list
- `Arrays.stream(T... A)` converts an array to a stream

`Stream`
- `.toArray()` returns an array containing the elements of this stream

Switch Statements
```java
int day = 4;
switch (day) {
    case 1:
        System.out.println("x");
        break; //the break keyword causes it to break out of the switch block
    case 2:
        System.out.println("y");
        break;
    default: //the default keyword is for when there is no case match
        System.out.println("no match lol"); 
}
```

`compare` vs `compareTo`
- `compare` takes in two parameters and returns an `int`, usually occurs when you implementing `Comparator<T>`
- `compareTo` takes in one parameter and returns an `int`, usually occurs when you implement `Comparable<T>` to your class

# Chapter 2 - Testability in OOP
Mutators and its effect on Testing
- Each method should return an object, so as to support method chaining
- Void methods should be avoided

Immutability
- Once an object is instantiated, it should not be modified
- Ensure by making all instance fields final
```java
public class Point {
    private final double x;
    private final double y;
```
- Methods should then return other immutable objects

Bottom-up Testing
- Test the base class first, once base class is completely tested and working, we go up to the class that extends from it.
- Reason being if there is a bug, we can isolate the problem to another class.

Factory Methods
- To prevent the creation of invalid objects, **static** factory methods can be used to check the validity of the input parameters.
```java
static Circle getCircle(Point centre, double radius) {
    if (radius > 0) {
        return new Circle(centre, radius);
    } else {
        return null;
    }
}
```
- These factory methods calls the constructors to instantiate objects only if parameters are valid
- Should use `Optional.empty()` if possible when generating a null object.

Inheritance
- Modify the accesibility of the constructor from `private` to `protected` for parent class.
- If a property of the parent class needs to be accesible, access modifer needs to be changed
```java
public class Circle {
    protected final double radius;
```

Cyclic Dependency
- These can come in two forms:
    1. Hard Dependencies: references to other classes in instance fields/variables
    2. Soft Dependencies: references to other classes in methods (i.e. parameters, local variables, return type)
- Dependencies of classes/components should not have cycles.

# Chapter 3 - Substitutability Principle in Object-Oriented Design
Overriding `Object`'s `equals` Method
```java
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    } else if (obj instanceof Point) {
        Point p = (point) obj;
        return Math.abs(this.x - p.x) < 1E-15 && Math.abs(this.y - p.y) < 1E-15;
    } else {
        return false;
    }
}
```
- TLDR:
    1. Check whether the objects are the same
    2. Check whether they share the same type
    3. Check the associated equality property
- Sidenote: You will have to override `hashCode` method as well:
    - `hashCode` is **not** a unique identifier for an object
    - `hashCode` is an int.
    - if two objects are equal, they must share the same hashCode as well.
    - used in hashMap and hashSet to find out how the object would be stored in a collection.

Composition (has-a relationship)
- `FilledCircle` has a `Circle`
```java
public class FilledCircle {
    private final Circle circle;
    private final Color color;
```

Inheritance (is-a relationship)
- `FilledCircle` is a `Circle`
```java
public class FilledCircle extends Circle {
    private final Color color;

    public FilledCircle(double radius, Color color) {
        super(Radius);
        this.color = color;
    }
```
- The `super` keyword is used for the following purposes:
    1. `super(..)` to access the parent's constructor
    2. `super.radius` or `super.toString()` can be used to make reference to the parent's properties or methods

Type-casting to a parent
- Parent's methods and attributes can be assessed.
- Only exception is overriden methods, the overriden methods will be invoked.
- Parent method can only be called via `super`.

Static (early) binding
- Takes place during compile time. Applies for static, private and final members (methods and variable).
- Compiler knows which one to call at compile time because it does not change and all the information is present.
```java
for (Circle circle : circles) {
    if (circle instanceof Circle) {
        System.out.println((Circle) circle);
    } else if (circle isntanceof FilledCircle) {
        System.out.println((FilledCircle) circle);
    }
}
```

Dynamic (late) binding
- Exact type of object is only determined at run time.
```java
for (Circle circle : circles) {
    System.out.println(circle);
}
```
- Polymorphism with dynamic binding allows implementations to be more easily extensible (open-closed principle)
- Does not require the client code (above) to be modified unlike static binding.

**Liskov Substituion Principle (LSP)**
- If S is a subclass of T, then an object of type T can be replaced by that of type S **without changing the desirable property of the program**
- Eg. if `FilledCircle` is a subclass of `Circle`, then everywhere circles can be used, we can replace a circle with a filled-circle

# Chapter 4 - Interface as the Abstraction Barrier

Abstract Class
```java
public abstract class Shape {
    double x,y;
    
    public abstract double getArea();
    public abstract doublle getPerimeter();
    public abstract Shape scale(double factor):
    public abstract String print();

    @Override 
    public String toString() {
        return "Area " + String.format("%.2f", getArea());
    }
}
```
- Abstract classes behave like a skeleton
- Abstract methods (no implementation required) and concrete methods are allowed.
- An object can extend from an abstract class
- It cannot be instantiated on its own

**Single Responsibility Principle**
- Rather than letting `Shape` be responsible for shape properties, as well as scaling and printing, we can separate the two latter funcitonalities into individual classes or entities.
- A class can only inherit from one parent class (Prohibited in Java to avoid the creation of weird objects)
- A class can however implement many interfaces e.g. `Scalable` and `Printable`

Interfaces
- Behaves as a contract
- Cannot be instantiated
- Depicts as:
    1. is-a relationship towards a non-concrete super-class
    2. can-do relationship
```java
public interface Shape {
    public double getArea();
    public double getPerimeter();
}

public interface Scalable {
    public Object scale(double factor);
}

public interface Printable {
    public abstract String print();
}
```

**Interface Segregation Principle (ISP)**
- Keep interfaces minimal.
- Split larger interfaces into smaller and more specific ones.

**Dependency Inversion Principle**
- Program to an interface, not an implementation
- Abstractions: Interface
- Details: Concrete implementations

Difference between concrete, abstract classes and interface:
- A **concrete class** is an actual implementation.
- An **interface** is a contract that specifies the abstraction that its implementer shuold implement
- An **abstract class** is a trade off between the two, i.e. when we need to have partial implementation of the contract
    - Typically a base class

Stubbing
- Commence testing a method without waiting for a dependency to complete.
- Dependency-injection via inheritance: `PointStub` is-a `Point`
- Correct implementation and it's stubbed version is exactly the same.

**Open Closed Principle (OSP)**
- Classes should be open for extension, but closed for modification
- A class can be extended without modifying the source code

`Final` keyword
- Used to explicitly prevent inheritance

**SOLID**
- Single responsibility principle (SRP)
- Open-closed principle (OCP)
- Liskov Substitution principle (LSP)
- Interface Segregation principle (ISP)
- Dependency Inversion principle (DIP)

# Chapter 5 - Generics and Variance of Types

Generic Type
- A type or method to operate on objects of various types while providing compile-time type safety
- `Queue<Circle> circleQueue = new Queue<Circle>()`
- Also known as parametric polymorphism
- Implements genric typing via **type erasure** for backward compatibility
    - Type argument is erased during compile time
    - Type parameter is replaced with either
        - Object if it is unbounded, or
        - the bound if it is bounded

Auto-boxing and Unboxing
- Only reference types allowed as type arguments; primitives need to be auto-boxed/unboxed
- E.g. `ArrayList<Integer>`
- Place an `int` value inside the arraylist causes it to be auto-boxed into `Integer`
- Taking an `Integer` value out of the arraylist causes it to be (auto-)unboxed into `int`

Wildcards
- `Shape[] shapes = new Circle[10]` works but `ArrayList<Shape> = new ArrayList<Circle>()` does not work
- In the spirit of the first one, you can do `ArrayList<?> anyList = new ArrayList<Circle>()` where `?` refers to a wildcard
- A wildcard is **not** a type

Upper-Bounded Wildcares
- `<? extends T>`
```java
static void getBurger(List<? extends Burger> burgerProducer) {
    for (Burger burger : burgerProducer) {
        System.out.println(burger);
    }
}
```

Lower-Bounded Wildercards
- `<? super T>`
```java
static void putBurger(List<? super Burger> burgerConsumer) {
    burgerConsumer.add(new Burger());
}
```

Get-Put Principle
- Covariant: use `extends` to get items from a **producer**
- Contravariant: use `super` to put items into a **consumer**
- PECS: producer extends consumer super

Java Collections Framework
- Collection-framework interfaces declare operations to be performed generically on various types of collections
    - Set: Collection that does not contain duplicates
    - List: Ordered collection that can contain duplicate elements
    - Map: A collection that associates keys to values and cannot contain duplicate keys
    - Queue: A FIFO collection that models a waiting line
```java
public interface Collection<E> extends Iterable<E> {

    boolean add(E e);
    boolean contains(Object o);
    boolean remove(Object o);
    void clear();
    boolean isEmpty();
    int size();
    Object[] toArray();
    <T> T[] toArray(T[] a);
    boolean addAll(Collection<? extends E> c);
    boolean containsAll(Collection<?> c);
    boolean removeAll(Collection<?> c);
    boolean retainAll(Collection<?> c);

}
```

`List<E>` interface
- `List<E>` interface extends `Collection<E>`
- `ArrayList` and `LinkedList` implements `List<E>`: `List<Circle> circles = new ArrayList<>()`
- A sort method is specified: `default void sort(Comparator<? super E> c)`
- the `default` method indicates that List<E> comes with a default sort implementation
    - does not need to implement again but can be overriden

`Comparator<T>` interface
- Contains the following abstract methods that needs to be implemented
    - Compare method: `int compare(T o1, T o2)`
    - Equals method:`boolean equals(Object obj)`
```java
import java.util.Comparator;

public class NumberComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer s1, Integer s2) {
        return s1 - s2;
    }

    @Override
    public boolean equals(Object obj) {
        return s1 == s2;
    }
}
```

Anonymous Inner Class
- Rather than creating another class, an anonymous inner class can be defined
```java
List<Integer> nums = new ArrayList<>();
nums.add(2);
nums.add(1);
nums.add(3);
nums.sort(new Comparator<Integer>() {
    @Override
    public int compare(Integer s1, Integer s2) {
        return s1 - s2;
    }
});
```

# Chapter 6 - Misc Java Bullshit

The `static` Keyword
- `static` can be used in the declaration of a field, method, block or class
- A `static` field is a class-level member, i.e. it is shared by all objects of the class
    - Can be used to aggregate data, e.g. number of circles
```java
public class Circle {
    private double radius;
    private static int numOfCircles = 0;

    public Circle(double radius) {
        this.radius = radius;
        Circle.numOfCircles++; // or this.numCircles++
    }
```
    - Use for constants, `static double final PI = 3.146;`
- `static` methods **belong to the class** instead of an object
- **Cannot be overriden** as `static` methods are resolved at compile time
- To access/mutate static fields, you can use a static method
```java
    public static int getNumOfCircles() {
        return Circle.numOfCircles;
    }
```

Nested classes
- Encapsulation: nested class only useful in its enclosing class
- Non-static (anonymous) inner classes can access all (even private) members of enclosing class
- `static` nested classes
    - can only access static members of enclosing class
    - top-level class cannot be made static
```
class Circle {
    private double radius;
    public Circle() {
        new CircleCreator().create(this);
    }
    private static class CircleCreator {
        private void create(Circle circle) {
        //pseudocode
        }
    }
```

Java Memory Model
- Comprises of three different areas:
    - Stack
        - LIFO stack **for storing actvation records of method calls**
        - method **local variables** are stored here
    - Heap
        - for storing Java objects upon invoking `new`
        - garbage collection is done here
    - Non-heap (Metaspace since Java 8)
        - for storing loaded classes, and other meta data
        - `static` fields are stored here

Handling Exceptions
- `try` block encompasses the business logic
- `catch` block handles exeptions
```java
try {
    FileReader file = new FileReader(args[0]); 
    Scanner scanner = new Scanner(file); 
    Point[] points = new Point[100];
    int numOfPoints = 0;
    while (scanner.hasNextDouble()) {
    double x = Double.parseDouble(scanner.next()); 
    double y = Double.parseDouble(scanner.next()); 
    points[numOfPoints] = new Point(x, y); numOfPoints++;
    }
    DiscCoverage maxCoverage = new DiscCoverage(points, numOfPoints); 
    System.out.println(maxCoverage);
} catch (FileNotFoundException ex) { 
    System.err.println("Unable to open file " + args[0] +
        "\n" + ex + "\n");
}
```

Catching Multiple Exceptions
- Multiple catch blocks defined to handle each exception
- Handling multiple exceptions in a single catch using `|`
- Optional `finally` block used to house-keeping tasks
```java
try {
    //random code
} catch (FileNotFoundException ex) {
    //do something
} catch (ArrayIndexOutOfBoundsExcpetion ex) {
    //do something else
} catch (NumberFormatException | NoSuchElementException ex) {
    //do other things
} finally {
    //do one last thing
}
```

Types of Exceptions
- A **checked exception** is one that the programmer should actively anticipate and handle
    - Most exceptions are unchecked, those that are checked usually arise because of the use of special classes e.g. `CompletableFuture`
    - E.g. when opening a file, a `FileNotFoundException` should be explicitly handled as it can be anticipated
- An **unchecked exception** is one that is unanticipated, usually the result of a bug
    - E.g. a `NullPointerException` surfaces when trying to call `p.distanceto(q)`, with `p` being `null`
    - **All checked exceptions** should be caught (catch) or propagated upwards (throw)
- Unchecked exceptions are sub-classes of `RunTimeException`
- All `Errors` are also unchecked

Examples of Exception:
- `RuntimeException` (unchecked)
- `ClassCastException` (unchecked)
- `ArithmeticException` (unchecked)
- `NullPointerException` (unchecked)
- `IndexOutOfBoundsException` (unchecked)
- `IllegalArgumentException` (unchecked)

Examples of Error:
- `AssertionError`
- `LinkageError`
- `VirtualMachineError`

`throw` an Exception
```java
public Circle(Point p, Point q, double radius) {
    if (p.distanceTo(q) > 2 * radius) {
        throw new IllegalArgumentException( 
            "Input points are too far apart");
    }
    if (p.equals(q)) {
        throw new IllegalArgumentException( 
            "Input points coincide");
    }
    this.radius = radius;
        this.centre = findCentre(p, q, radius);
}
```

Generating Exception
- User defined exception by inheriting from existing ones
```java
class IllegalCircleException extends IllegalArgumentException { 
    Point p;
    Point q;

    IllegalCircleException(String message) { 
        super(message);
    }

    IllegalCircleException(Point p, Point q, String message) { 
        super(message);
        this.p = p;
        this.q = q; 
    }

    @Override
    public String toString() {
        return p + ", " + q + ": " + getMessage();
    }
}
```

Notes on Exceptions
- Only create your own exceptions if the exception you need doesn't exist
- When overriding a method that throws a checked exception, the overriding method must throw only the same or more specific exception
- Avoid catching `Exception`, aka **Pokemon Exception Handling**
- Handle exceptions at the appropriate abstraction level, do not just throw and break the abstraction barrier.

Assertions
- Exceptions used to handle user mishaps, assertions used to prevent bugs.
- Assertions are conditions that should be true at a particular point, say in a method, there are two types:
    - **Preconditions** are assertions about a program's state when a program is invoked
    - **Postconditions** are assertions about a program's state after a method finishes
- There are two forms of assert statement:
    - `assert expression;`
    - `assert expression1 : expression2;`

Enumeration
- An `enum` is a special type of class used for defining constants
```java
enum Color {
    BLACK, WHITE, RED, BLUE, GREEN, YELLOW, PURPLE
}
Color color = Color.BLUE;
```
- `enum` are type-safe; color = 1 is invalid
- Each constant of an `enum` type is an instance of the `enum` class and is a field declare with `public static final`
- Constructors, methods, and fields can be defined in enums

Access Modifiers
- There is a default modifier other than `public`, `private` and `protected`
- Java adopts an additional `package` abstraction mechanism that allow grouping of relevant classes/interfaces together under a namespace, just like java.lang
- `protected` field can be accessed by other classes within the same package
- The access level (most restrictive first) is given as follows:
    - private (visible to the class only)
    - default (visible to the package)
    - protected (visible to the package and all sub classes)
    - public (visible to the world)

Creating Packages
- Include the following line at the top of the java files: `package cs2030.shapes;`

# Chapter 7 - The Case Against The Null Reference

Maybe an Object
- Creating a `Maybe` wrapper that wraps around an object.
- Basically Optionals
```java
public class Maybe<T> {
    private final T thing;

    private Maybe() {
        thing = null;
    }

    public Maybe(T thing) {
        this.thing = thing;
    }

    public static <T> Maybe<T> empty() {
        return new Maybe<T>();
    }
}
```
Chaining Methods to a `Maybe` Object
- A `Maybe` object needs to be able to take in methods
    - Intermediate Operations
    - Terminal Operations
- We can pass an "action" to a method using a interface with an abstract doit method
```java
interface Actionable<T> {
    void doit(T t);
}
```
- Behaves similar to a `Consumer<T>`

From Anonymous Inner Class to Lambda
```java
new Actionable<>() {
    public void doit(Circle c) {
        System.out.println(c.contains(new Point(0,0)));
    }
}
```
- Only the actual method in the anonymous inner class is useful, the class name does not add value
- If there is only a single abstract method in the class, then the method name (doit) does not add value
- A **lambda epxression** can be used a short hand:
```java
Circle.getCircle(new Point(0, 0), 1).ifPresent(
    (Circle c) -> System.out.println(c.contains(new Point(0, 0))));
```

Lambda Expression
- Lambda syntax: `(parameterList) -> {statements}`
- Other lambda variants:
    - Inferred parameter type: `(x, y) -> {return x * y;}`
    - Body contains a single expression: `(x, y) -> x * y`
    - Only one parameter: `x -> 2 * x` (don't need brackets for input)
- Methods can now be treated as values!
    - Assign lambdas to variables
    - Pass lambdas as arguments to other methods
    - Return lambdas from methods (your method now becomes a variable)

`map`-ping from One Value to Another
```java
interface Mappable<T,R> {
    R apply(T t);
}

public class Maybe<T> {
    public <R> Maybe<R> map(Mapplable<T,R> mapper) {
        if (thing == null) {
            return Maybe.empty();
        } else {
            return new Maybe<R>(mapper.apply(thing));
        }
    }
```
- `Mappable` here accepts a lambda that returns a value

TBH This is just Java's `Optional` Class
- Some familiar methods of the `Optional` class includes:
```java
public void ifPresent(Consumer<? super T> action);
public <U> Optional<U> map(Function<? super T, ? extends U> mapper)
public Optional<T> filter(Predicate<? super T> predicate)
```
- `Consumer<T>` is a functional interface with a single abstract method `accept(T t)`
- `Function<T,R>` has `R apply(T t)`
- `Predicate<T>` has `boolean test(T t)`
- Static methods include:
```java
Optional.of(T value)
Optional.empty()
Optional.ofNullable(T value)
```

Local Class and Variable Capture
- Lambdas and anonymous classes declared inside a method are called local classes
- Local class (like local variable) is scoped within the method
    - has access to the varibales of the enclosing method/class
    - the local class actually **makes a copy of these variables inside itself**, i.e. the local class captures the local variables.
- Java only allows a local class to access variables that are explicitly declared final or effectively (or implicitly) final (because a **copy** is being made)

`flatMap`-ping from One Value to Another (instead of `Map`)
- We can use `flatMap` to "flatten" two `Optionals` into one
    - in layman terms, to apply the mapping function on the value of an `Optional` and flatten the resulting nested `Optional`
```java
public <U> Optional<U> flatMap(
    Function<? super T, ? extends Optional<? extends U>> mapper)
```
- An example to make you feel sane:
```java
Circle.getCircle(new Point(0, 0), 1).flatMap(x -> x.getCentre());
```
- In this case the `getCentre()` method actually returns an `Optional<Circle>`, using `Map` we would get an `Optional<Optional<Circle>>`, but with `flatMap` the object stored is replace with the circle in the new `Optional`.
- BAM! You get `Optional<Cirle>`

# Chapter 8 - Towards Functional and Declarative Programming

Pure Function
- A pure function is one that takes in arguments and returns a deterministic value, witth no other side effects
    - Pureness does not means it is correct
- Side effets:
    - Program input and output
    - Throwing exceptions
    - Modifying external state
- Absence of side effeccts is a necessary condition for **referential transparency**:
    - Any expression can be replaced by its resulting value, without having to change the property of the program

Handling Side Effects within a Context
```java
<R> IList<R> map(Function<T, R> f) {
    ArrayList<R> newList = new ArrayList<>(); /* create new list */
    for (T items : list) { /* iterate through the old list */
        newList.add(f.apply(item)); /* add into new list after applying function f */
    }
    return new IList<R>(newList);
}
```

Functor
- `map` in both `Optional` and `IList` implements the following:
```java
interface Functor<T> {
    public <R> Functor<R> f(Function<T,R> func);
}
```
- A functor is a box that functions can be applied on
- Must obey two functor laws:
    - if `func` is an identity function `x -> x`, then it should not change the functor
    - if `func` is a composition `gh`, then the resulting functor should be the same as calling f with h and then with g

Monad
- This is a functor with built in operations.
- `flatMap` in `Optional` implements the following:
```java
interface Monad<T> {
    public Monad<T> of(T value);
    public <R> Monad<R> f(Function<T, Monda<R>> func);
}
```
- Monad laws:
    - Left identity: `Monad.of(x).flatMap(f) = f.apply(x)`
    - Right identity: `monad.flatMap(x -> Monad.of(x)) = monad`
    - Associative: `monad.flatMap(f).flatMap(g) = monad.flatMap(x -> f.apply(x).flatMap(g))`

External Iteration
- Another example of handling context, that of looping
- An **imperative** loop specifies **how** to loop and sum
```java
int sum = 0;
for (int i = 1; i <= 10, i++) {
    sum += i;
}
```
- Realise the variables i and sum **mutates** at each iteration
- Errors could be introduced when
    - `sum` is initialised wrongly before the loop
    - looping variable `x` is initialised wrongly
    - loop condition is wrong
    - increment of `x` is wrong
    - aggregation of `sum` is wrong

Internal Iteration
- A declarative approach that specifies **what** to do
```java
int sum = IntStream
    .rangeClosed(1, 10)
    .sum();
```
- No need to specify how to iterate through elements or use any mutatable variables
- IntStream handles all the iteration details

Intermediate and Terminal Operations
- Intermediate operations (like `map`) use **lazy evaluation**
- Does not perform any operations until a terminal op is called
- Terminal operation uses eager evaluation, i.e. perform the requested operation when they are called

Stateless vs Stateful Operations
- Intermediate stream operations like `filter` and `map` are **stateless**, i.e. processing one stream element does not depend on other stream elements
- There are hwoever, **stateful** intermediate operations that depend on the current state e.g. `sorted`, `limit`, `distinct`

Method References
- `.forEach(x -> System.out.println(x))` can be replaced by `.forEach(System.out::println)`
- Types of method references:
    - reference to a static method
    - reference to an instance method
    - reference to a constructor

Boolean Terminal Operations
- Useful terminal operations that return a `boolean` result:
    - `noneMatch` returns `true` if none of the elements pass the given predicate
    - `allMatch` returns `true` if every element passes the given predicate
    - `anyMatch` returns `true` if at least one element passes the given predicate

Stream Elements
- Each inter operation results in a new stream
- Each new stream is an object representing the processing steps that have been specified up to that point
- When initiating a stream pipeline with a terminal op, the inter op prcoessing steps are applied one stream element after another
- Stream elements can only be consumed once
    - Cannot iterate through a stream multiple times
    -
User-defined Reductions
- Terminal operations are specific implementations of `reduce`
- Using `reduce` in place of `sum`
```java
IntStream
    .of(values)
    .reduce(0, (x, y) -> x + y)
```
- First arg to `reduce` is the op's identity value
- Second arg is the lambda that receives two int value, adds them and returns the result
    - First calculation uses identity value 0 as left operand and the result of the prior calculation subsequently
    - Right operand is the new input
    - If stream is empty, identity is returned

Infinite Stream
- Lazy evaluation allows us to work with infinite streams as not every thing needs to be calculated
- Intermediate operations can be used to restrict the total number of elements
- `iterate` generates an ordered sequence starting using the first argument as a seed value
- E.g. to find first 500 primes:
```java
IntStream
    .iterate(2, x -> x + 1)
    .filter(x -> isPrime(x))
    .limit(500)
    .forEach(System.out::println);
```

`Stream<T>`'s `reduce` Operator
- it is declared as: `T reduce(T identity, BinaryOperator<T> accumulator)`
- Overloaded `reduce` method

# Chapter 9 - The Art of Laziness
Towards An Immutable Implementation
- What defines an IFL?
    - The head value
    - The tail list
    - Whether the list is empty

- `Suppliers` are used for delayed data
```java
class IFLImpl<T> implements IFL<T> {
    private final Supplier<T> head;
    private final Supplier<IFLImpl<T>> tail;

    private IFLImpl(Supplier<T> head, Supplier<IFLImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    boolean isEmpty() {
        return false;
    }
}
```

# Chapter 10 - From Sequential To Parallel Streams

Parallel Streams
- Uses a common `ForkJoinPool` via the static `ForkJoinPool.commonPool()` method
- Parallelizing the search for primes
```java
long count = IntStream.range(2_000_000, 3_000_000)
    .parallel()
    .filter(x -> isPrime(x))
    .count();
```
- `parallel()` intermediate op has to be placed right after a datasource
    - turns on a boolean flag to switch the stream pipeline to be parallel
    - invoked anywhere between the data source and terminal
- Stream is split into multiple chunks, with each chunk processed independently and result summarized at the end

Debuggin Parallel Streams
- To time the execution of a process
    - `java.time.Instant`'s `now()` method returns the current `Instant` from the sys clock
    - `java.time.Duration`'s `between()` returns the `Duration` of two `Instances` (an implementation of `Temporal`)
    - `Duration`'s `toMillis()`/`toNanos()`/`...` extracts the desired representation of the duration
```java
java.util.Instant;
java.util.Duration;

Instant start, stop;
start = Instant.now();
/* perform some task */
stop = Instant.now();

long timeInMillis = Duration.between(start, stop).toMillis();
```
- To debug and manage each execution thread
    - `Thread.currentThread()`
    - `Thread.sleep(long millis)` cause the currently executing threa to sleep for specified number of ms
        - Used within a `try.. catch` block
        - Example, letting a thread sleep for one second
```
try {
    ...
    Thread.sleep(1000);
    ...
} catch (InterruptedExeception e) { }
```

Correctness of Parallel Streams
- To ensure correct parallel execution, stream operations
    - must not interfere with stream data (true for sequential streams also)
    - preferably stateless with no side effect (prevent ConcurrentModificationExeception)
- The following is erroneous:
```java
list.parallelStream()
    .filter(x -> isPrime(x))
    .forEach(x -> result.add(x));
```
- Use `.collect` instead:
```java
list.parallelStream()
    .filter(x -> isPrime(x))
    .collect(Collectors.toList());
```

- Can also use `.forEachOrdered(Consumer<? super T> action)`, or a thread-safe list `CopyOnWriteArrayList`

Inherently Parallelizable `reduce`
- Consider `Stream`'s three-argument `reduce` method:
```java
<U> U reduce(U identity, 
    BiFunction<U, ? super T, U> accumulator, 
    BinaryOperator<U> combiner)

interface BiFunction<T,U,R> {
    abstract R apply(T t, U u);
}

interface BinaryOperator<T> {
    /* this implements BiFunction and has an apply method as well */
}
```
- Rules to follow:
    - `combiner.apply(identity, i)` must be equal to `i`.
    - `combiner` and `accumulator` must be associative, i.e. order of application does not matter
    - `combiner` and `accumulator` must be compatible, i.e. `combiner.apply(u, accumulator.apply(identity, t))` must be equal to `accumulator.apply(u, t)`

Fork and Join in Parallel Streams
- Parallelizing a trivial task actually creates more work in terms of parallelizing overhead
- Only worthwhile if task is complex enough that the benefit of parallelization outweighs the overhead.

# Chapter 11 - Fork/Join Framework

Fork and Join
```java
B b = f(a); /* this start first */
C c = g(b); /* can be done concurrently */
D d = h(b); /* can be done concurrently*/
E e = n(c,d); /* join */

CompletableFuture<B> b = CompletableFuture.supplyAsync(() -> f(a));
CompletableFuture<C> c = b.thenApply(x -> g(x);
CompletableFuture<C> d = d.thenApply(x -> h(x);
CompletableFuture<E> e = c.thenCombine(d, (x, y) -> n(x,y));
e.join();
```
- `f(a)` invoked before `g(b)` and `h(b)`; `n(c, d)` invoked after
- If `g(b)` and `h(b)` does not produce side effects, then parallelize
- `fork` task `g` to exceute at the same time as `h`, and `join` back task `g` later
```java
import java.util.concurrent.RecursiveTask;

class Summer extends RecursiveTask<integer> {
    ...
    @Override
    protected Integer compute() {
            if (high - low < threshold) {
                int sum = 0;
                for (int i = low; i < high; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                int middle = (low + high) / 2;
                Summer left = new Summer(array, low, middle);
                Summer right = new Summer(array, middle + 1, high);
                left.fork();
                return right.compute() + left.join();
            }
    }
}
```

Thread Pools
- Java maintains a pool of worker threads
    - Each thread is an abstraction of a running task
    - Task submitted to the pool for execution, and joins the global queue or worker queue
    - Worker thread picks a task from the queue to execute
- `ForkJoinPool` is the class the implements the thread pool for `RecusiveTask` (a sub-class of `ForkJoinTask`)
- To submit the `task` to the thread pool for execution, either
    - `task.compute()` that invokes task immediately; may result in stack overflow if too many recursive tasks
    - `invoke(task)` that gets the task to join the queue, waiting to be carried out by a worker (recommended)

Order of Fork/Join
- Fork-join pair acts like a call (fork) and return (join) from a parallel recursive function.
    - Return (joins) should be performed **innermost-first**
```java
a.fork(); b.fork();
b.join(); a.join();
```
- This is subtantially more efficient than joining task a before task b
- Work-stealing threadpools have a fixed number of threads
    - Any blocking operation will reduce overall performance

- `.join()` returns the computed result and can be called multiple times because it's internal `.isAlive` will be false

Overhead of Fork/Join
- Forking and joining creates additional overhead
    1. Wrap the computation in an object
    2. Submit object to a queue of tasks
    3. Workers go through the queue to execute task

Explore different variants and combintions of `fork`, `join` and  `compute` invocations
- Most appropriate:
```
f1.fork();
return f2.compute() + f1.join();
```
- Works, but slow since Java subexpressions are evaluated left to right, i.e. for A + B, A is evaluated first before B. So f1.join() needs to wait for f1.fork() to complete before f2.compute() can be evaluated.
```
f1.fork();
return f1.join() + f2.compute();
```
- Sequentially recursive, not different from (b) but still slightly faster as there is no overhead involved in forking and joining. Everything is done by the main thread:
```
return f1.compute() + f2.compute();
```
- Apart from the first recursion, main thread delegates all other work to worker threads in the common pool:
```
f1.fork();
f2.fork();
return f2.join() + f1.join();
```
- a `fork()` must be followed by a `join()` to get the result back. 
# Chapter 12 - Asynchronous Programming

Asynchronous Computation
- Create a thread that runs the `compute` method
```java
class Async {
    public static void main(String[] args) { 
        System.out.println("Before calling compute()");

        Thread t = new Thread(() -> new UnitTask().compute());
        t.start();
        
        System.out.println("After calling compute()");
    }
}
```
- Passing a `Runnable` to the `Thread` constructor (similar to `Supplier`)
- `Runnable` is a SAM (Single Abstract Method) with the abstract `run()` method
- Start the thread with `start()` method (cf. `run()` method)

Thread Completion via `join()`
- Wait for thread to complete using the `join` method
```java
try {
    System.out.println("Before calling compute()");

    Thread t = new Thread(() -> new UnitTask().compute());
    t.start();

    System.out.println("Do independent task...");
    otherTask();

    System.out.println("Waiting at join()");
    t.join(); 
    System.out.println("After calling compute()");
} catch (InterruptedException e) { }
```
- `join()` throws `InterruptedException` if the current thread is interrupted

Callback
- Rather than busy-waiting, a callback can be specified
- A callback (more aptly call-after) is any executable code that is passed as an argument to other code so that the former can be called back (executed) at a certain time
- The execution may be immediate (synchronous callback) or happen later (asynchronous callback)
- **Avoid repetitive checking** to see if the asynchronous task completes
- Callback may be invoked from a thread but is not a requirement
- An observer pattern can be utilized where the callback can be invoked, say `notifyListener`

Creating a Listener
- The conventional way of creating a listener is via an interface
- Motivated by the Observer pattern
```java
public interface listener {
    public void notifyListener();
}
```

From `Runnable` to `Callable` Interface
- Callable runs in a separate thread and returns a value when completed, as well as allowing exceptions to be thrown
- SAM with `abstract <V> call()` method
- Pass `Callable` (or `Runnable`) to the `submit` method of an `ExecutorService`
    - `ForkJoinPool` is an implementor of `ExecutorService`
- `submit` method returns a `Future` object
    - Returns `Future<V>(Callable)` or `Future<?>(Runnable)`
- `Future`'s `get()` method **waits for execution to complete and retrieves result**; returns `null` in the case of `Runnable`
- `get` method requires both `InterruptedException` and `ExecutionException` to be caught

```java
class Async {
        public static void main(String[] args) { 
            System.out.println("Before calling compute()");

            Future<Integer> future = new ForkJoinPool().submit(
                    () -> new UnitTask().compute());

            try {
                System.out.println("Do independent task...");
                indeTask();
                System.out.println("Done indepndent task...");

                Integer result = future.get();
                System.out.println("After compute(): " + result);
            } catch (InterruptedException | ExecutionException e) { }
        }
}
```

Useful Methods of `Future` interface
- `isDone()` returns `true` when the task completes
- `get()` returns the result of the computation, waiting for it if needed
- `get(timeout, unit)` returns the result of the computation, waiting for up to the timeout period if needed (`unit` is in the type of `DAYS`, `SECONDS`, `MINUTES` etc.)
- `isCancelled()` returns true of the task has been cancelled
- `cancel(interrupt)` tries to cancel the task - if interrupt is true, cancel even if the task has started; otherwise, cancel only if the task is still waiting to get started

From `Future` to `Promise`
- A future is a read-only container of a yet-to-exist result
- A promise is a future that can be completed, hence in Java a promise is implemented as `CompletableFuture`
- `CompletableFuture` implements Future, and the calle can construct it, return it immediately to the caller and start the async task

Implementing Promise via `CompletableFuture`
```java
public Future<integer> callee() {
    CompletableFuture<Integer> promise = new CompletableFuture<>();

    new ForkJoinPool().submit(() -> {
        int result = new UnitTask().compute();
        promise.complete(result);
    });
    
    return promise;
}
```
- The caller (e.g. main) is only aware of the `Future` object due to polymorphism: `Future<Integer> future = new Async().callee();`
- Unlike `Future`, `CompletableFuture` may be explicitly completed by settings its value and status

Encapsulating Asynchronous Logic
- static methods `runAsync` and `supplyAsync` creates `CompletableFuture` instances of out `Runnable` and `Suppliers` respectively
```java
public static void main(String[] args) { 
    System.out.println("Before calling compute()");

   CompletableFuture<Integer> future = CompletableFuture
        .supplyAsync(() -> new UnitTask().compute());
    try {
        System.out.println("Do independent task...");
        indeTask();
        System.out.println("Done indepndent task...");

        Integer result = future.get();
        System.out.println("After compute(): " + result);
    } catch (InterruptedException | ExecutionException e) { }
}
```
- `ThenAccept()` accepts a `Consumer` and the `Future` chain passes the result of computation to it; returns a `CompletableFuture<Void>`
- `CompletableFuture` is meant to be written like a stream, with method chaining e.g.
```java
CompletableFuture<Void> future = 
    CompletableFuture
    .supplyAsync(() -> new UnitTask().compute())
    .thenAccept(s -> 
        System.out.println("After compute(): " + s));
System.out.println("Do independent task");
doSomethingElse();
System.out.println("Done independent task");
future.join();
```
- Just like `get()`, the `join()` method is blocking and returns the result when complete

Converting a Synchronous method to an Asynchronous method
- Base method:
```java
static A foo(A a) {
    return a.incr().decr();
}
```
- With `supplyAsync`:
```java
static CompletableFuture<A> foo(A a) {
    return CompletableFuture
        .supplyAsync(() -> a.incr().decr());
}
```
- With `supplyAsync` and `thenApply`:
```java
static CompletableFuture<A> foo(A a) {
    return CompletableFuture
        .supplyAsync(() -> a.incr());
        .thenApply(x -> x.decr());
}
```
- With `SupplyAsync` and `thenApplyAsync`:
```java
/* thenApplyAsync is run in a separate thread */
static CompletableFuture<A> foo(A a) {
    return CompletableFuture
        .supplyAsync(() -> a.incr());
        .thenApplyAsync(x -> x.decr());
}
```
- To wait for the result:
```java
A a = foo(new A());
// do something else
a.join();
```
- How to use `thenCombine`:
```java
static CompletableFuture<A> bar(A a) {
    return CompletableFuture
        .supplyAsync(() -> a.incr);
}

CompletableFuture<A> b = foo(new A()).thenCompose(x -> bar(x));
System.out.println(b.join());
```

- `CompletableFuture`s need to always be joined back.
- `CompletableFuture` is a monad:
    - `completedFuture` is equivalent to `of`
    - `thenCompose` is `flatMap`
    - `thenApply` is `map`
    - `thenAccept` is `accept` and returns `void`
    - `thenCombine` takes in a `CompletionStage` and a merging `BiFunction`

```java
static CompletableFuture<A> baz(A a, int x) {
    if (x == 0) {
        return CompletableFuture.completedFuture(new A(0));
    } else {
        return CompletableFuture.supplyAsync(() -> a.incr().decr());
    }
}

CompletableFuture<Void> all = CompletableFuture.allOf(
        foo(new A()),
        bar(new A()),
        baz(new A(), 1));
all.join();
System.out.println("done!");
```

- How to use `handle()` in `CompletableFuture` (`handle()` takes in a bifunction with inputs being result and throwable):
```java
CompletableFuture<A> exec = CompletableFuture
    .supplyAsync(() -> new A().decr().decr())
    .handle((result, exception) -> {
        if (result == null) {
            System.out.println("ERROR: " + exception);
            return new A();
        } else {
            return result;
        }
    });

System.out.println(exec.join());
```
