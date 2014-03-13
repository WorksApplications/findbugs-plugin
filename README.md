# FindBugs Plugins
FindBugs plugin set from Works Applications. Mainly designed to support J2EE technology and huge project.

[![Build Status](https://secure.travis-ci.org/WorksApplications/findbugs-plugin.png)](http://travis-ci.org/WorksApplications/findbugs-plugin)

# how to use with Maven

To use this product, please configure your findbugs-maven-plugin like below.

```xml
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>findbugs-maven-plugin</artifactId>
        <version>2.5.2</version>
        <configuration>
          <plugins>
            <plugin>
              <groupId>jp.co.worksap.oss</groupId>
              <artifactId>findbugs-plugin</artifactId>
              <version>0.0.2-SNAPSHOT</version>
            </plugin>
          </plugins>
        </configuration>
      </plugin>
```

# history

## 0.0.2

- added BrokenImmutableClassDetector
- added LongIndexNameDetector
- added LongTableNameDetector
- added LongColumnNameDetector
- added UnknownNullnessDetector
- added UndocumentedIgnoreDetector
- added ImplicitLengthDetector
- added ImplicitNullnessDetector
- added ColumnDefinitionDetector

## 0.0.1

- First release

# copyright and license

    Copyright 2013 Works Applications. Co.,Ltd.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
