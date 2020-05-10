# covid19-babashka

## Idea

Idea from Mohammed Aboullaite on Twitter: https://twitter.com/laytoun/status/1259198700397776901

Got interested in implementing the same with Clojure and Babashka: https://twitter.com/algrison/status/1259393589114789889

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
