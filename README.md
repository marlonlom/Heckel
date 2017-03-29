# Heckel
Simple image loader library for android.

## History:

*Heckel* stands for [_Erich Heckel_](https://en.wikipedia.org/wiki/Erich_Heckel) (31 July 1883 in Döbeln – 27 January 1970 in Radolfzell). he was a German painter and printmaker. 



## Usage:

### Import as a dependency:

Gradle:

```
compile 'com.github.marlonlom:heckel:$latestVersion'
```

Maven:

```xml
<dependency>
  <groupId>com.github.marlonlom</groupId>
  <artifactId>heckel</artifactId>
  <version>$latestVersion</version>
</dependency>
```

### Use it in your code:


```java

final ImageView mDemoImageView = (ImageView) findViewById(R.id.imageview_demo);

final String imageUrl = "-image url here-";
/* TLDR additionally, you can use resource string id for getting the image url.*/

Heckel.with(mDemoImageView).load(imageUrl);

```

## Contact me

 - []()Follow me on **Twitter**: [**@Marlonlom**](https://twitter.com/marlonlom)
 - Contact me on **LinkedIn**: [**Marlonlom**](https://co.linkedin.com/in/marlonlom)


### License

```
Copyright 2017 marlonlom

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
