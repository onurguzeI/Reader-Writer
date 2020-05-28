# Reader-Writer

Readers-writers problem, in which several processes (readers and writers) are trying to access shared variables. 
Obviously, if two readers access the shared data simultaneously, no adverse effects will result, hence, they are allowed to access. 
However, if a writer and some other process (either a reader or a writer) access the data simultaneously, chaos may ensue. 
To ensure that these difficulties do not arise, we require that the writers have exclusive access to the shared data while writing to the data.

The solution guarantees that:

 If a writer has begun writing process, then

- No additional writer can perform write function
- No reader is allowed to read

If 1 or more readers are reading, then

- Other readers may read as well
- No writer may perform write function until all readers have finished reading]]

### How to Use 

Update the reader and writer count in the algorithm as shown below and compile it

```java
public static final int readers = [number_of_readers];
public static final int writers = [number_of_writers];
```
   
 
