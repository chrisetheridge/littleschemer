#! /bin/bash -e -x

# [ profile ]
clj -A:$1 -C:$1 -R:$1 -m ether.littleschemer.repl 6661
