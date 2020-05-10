# covid19-babashka

## Running

```sh
./covid19.clj
./covid19.clj -c france
./covid19.clj -c france -g
```

## Dependencies

### clojure table 

You need clojure table in your deps:

```sh
export BABASHKA_CLASSPATH=$BABASHKA_CLASSPATH:$(clojure -Spath -Sdeps '{:deps {table {:mvn/version "0.5.0"}}}')
```

### asciigraph

You also need asciigraph installed on your system

```sh
go get github.com/guptarohit/asciigraph
go install github.com/guptarohit/asciigraph/cmd/asciigraph
```
