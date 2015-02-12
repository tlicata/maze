Maze
====

I was reading the (very excellent (so far)) "Clojure Programming" by
Chas Emerick, Brian Carper, & Christophe Grand when I came across
their maze generator example.

I had been considering the idea of maze generation since I had heard
it mentioned by Jack Danger as he was telling our students about some
of the interview questions he asks at Square.

Well, I hate hearing about interview questions I don't know how to
solve and I also wanted to really understand the point(s) that
"Clojure Programming" was trying to make, so I thought I'd implement
what I could.

Additionally, the example in the book is written in Clojure and Java
Swing.  I wasn't especially excited about that so I thought I could
implement it using ClojureScript and render the mazes in the browser
instead.

Run Locally
-----------

`index.html` and `index_prod.html` load the development (debuggable)
and production (optimized) builds of the ClojureScript, respectively.
Before loading either of those files in your browser you'll need to
convert the ClojureScript into JavaScript by running one of the
commands below.

```bash
lein cljsbuild once      # will build dev and production output
lein cljsbuild auto      # will watch files for changes and recompile
lein cljsbuild once dev  # will only build dev profile once
lein cljsbuild auto prod # will build production output as files change
```
