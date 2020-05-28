
### parseany.go修改的地方
```java
package main

/*
#include <stdlib.h>
*/
import "C"

import (
    ...
    "unsafe"
)

//export ParseAny
func ParseAny(datestr *C.char) *C.char {
    p, err := parseTime(C.GoString(datestr), nil)
    defer C.free(unsafe.Pointer(datestr))
    if err != nil {
        return nil
    }
    var t time.Time
    t, err = p.parse()
    if err != nil {
        return nil
    }
    return C.CString(t.Format("2006-01-02 15:04:05"))
}

//export ParseLocal
func ParseLocal(datestr *C.char) *C.char {
    p, err := parseTime(C.GoString(datestr), time.Local)
    defer C.free(unsafe.Pointer(datestr))
    if err != nil {
        return nil
    }
    var t time.Time
    t, err = p.parse()
    if err != nil {
        return nil
    }
    return C.CString(t.Format("2006-01-02 15:04:05"))
}

//export ParseStrict
func ParseStrict(datestr *C.char) *C.char {
    p, err := parseTime(C.GoString(datestr), nil)
    defer C.free(unsafe.Pointer(datestr))
    if err != nil {
        return nil
    }
    if p.ambiguousMD {
        return nil
    }
    var t time.Time
    t, err = p.parse()
    if err != nil {
        return nil
    }
    return C.CString(t.Format("2006-01-02 15:04:05"))
}
```

### 编译方法
#### Mac
```java
go mod init dateparse
go build -buildmode=c-shared -o libdate.dylib dateparse/parseany.go
```
将libdate.dylib拷贝至src/main/resource/darwin目录。

#### Windows
```java
go mod init dateparse
go build -buildmode=c-shared -o libdate.dll dateparse/parseany.go
```
将libdate.dll拷贝至src/main/resource/win32-x86-64目录。

#### Linux
```java
go mod init dateparse
go build -buildmode=c-shared -o libdate.so dateparse/parseany.go
```
将libdate.so拷贝至src/main/resource/linux-x86-64目录。

### xgo多平台编译
```java
cp -r dateparse $GOPATH/src
cd $GOPATH/src/dateparse
docker pull techknowlogick/xgo:latest
go get src.techknowlogick.com/xgo
xgo -buildmode=c-shared -out libdate --targets=linux/amd64,darwin/amd64,windows/* .
```