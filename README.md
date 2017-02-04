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
    byte[] hash = hh.getHash();  // Use this for your password...
    byte[] salt = hh.getHash();  // Store this...
    
    assertTrue(hasher.isSame(hh, text));
    assertTrue(hasher.isSame(salt, hash, text); // this would work too
    assertFalse(hasher.isSame(salt, hash, text + "1"));
    
### Password usage

One possible usage is to generate the salt based on the website, and hash your
password. Though not completely recommended, its better then nothing.
 
    Hasher hasher = hasherBuilder
          .hashAlgorithm(SupportedHashAlgorithm.getMinSCryptAlgo())
          .build();
    String text = "blah";
    byte[] salt = "www.google.com".getBytes(CHARSET);
    byte[] hash = hasher.hash(salt, text);
    String useThis = StringManipulator.toHex(hash); //51e152d7baa7b61d9eda4f2c31163b2fb3ba1386c437be6abd071d956b4fc697
    String orThis = StringManipulator.toString(hash); // though ymmv as this can be: 흒Ꞻᶶ�ᘱ⼻몳蘓㟄檾޽锝佫韆
    String base64 = StringManipulator.toBase64(hash); // eh...  UeFS17qnth2e2k8sMRY7L7O6E4bEN75qvQcdlWtPxpc=
    
### Customizable

You can easily set your own hashing algorithm if you are so inclined.

     Hasher hasher = hasherBuilder
                .saltSize(16)
                .hashAlgorithm((salt, bytes) -> myDigester.digest(salt, bytes))
                .randomProvider(Random::new) // Really, don't do this.
                .build();

## Gradle ##
    compile "com.codeheadsystems:shash:1.0.2"

## Maven ##
    <dependency>
      <groupId>com.codeheadsystems</groupId>
      <artifactId>shash</artifactId>
      <version>1.0.2</version>
    </dependency>
