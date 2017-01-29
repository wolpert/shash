# shash

A flexible way to hash text. Useful for password creation

### Secure

Open-source project under the BSD license. Default usage to hashing with
SCrypt, with 2^20 iterations, but you can reduce that to 2^14 iterations.
Default salt size is 256 bits, which can be changed. Uses the SecureRandom
class, but you can modify that too in the hashBuilder.

### Usage (tl;dr)

    Hasher hasher = new HasherBuilder
                 .hashAlgorithm(SupportedHashAlgorithm.getSCryptAlgo())
                 .build();
    HashHolder hh = hasher1.hash(text);
    byte[] hash = hh.getHash();  // Store this...
    byte[] salt = hh.getHash();  // And this...
    
    assertTrue(hasher.isSame(hh, text));
    assertTrue(hasher.isSame(salt, hash, text); // this would work too
    assertFalse(hasher.isSame(salt, hash, text + "1"));
    
### Customizable

You can easily set your own hashing algorithm if you are so inclined.

     Hasher hasher = hasherBuilder
                .saltSize(16)
                .hashAlgorithm((salt, bytes) -> myDigester.digest(salt, bytes))
                .randomProvider(Random::new) // Really, don't do this.
                .build();

## Gradle ##
    compile "com.codeheadsystems:shash:1.0.0"

## Maven ##
    <dependency>
      <groupId>com.codeheadsystems</groupId>
      <artifactId>shash</artifactId>
      <version>1.0.0</version>
    </dependency>
