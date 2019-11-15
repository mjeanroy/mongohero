#!/bin/bash

##
# The MIT License (MIT)
#
# Copyright (c) 2019 Mickael Jeanroy
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
##

echo "[info] Importing zips collection"
mongoimport --mode=upsert --db mongohero --collection zips --file zips.json

echo "[info] Importing movies collection"
mongoimport --mode=upsert --db mongohero --collection movies --file movies.json

echo "[info] Creating some indices"
mongo <<EOF

use mongohero;

db.zips.createIndex({city: 1}, {background: true});
db.zips.createIndex({state: 1, city: 1}, {background: true});

db.movies.createIndex({year: 1}, {background: true});
db.movies.createIndex({director: 1, actors: 1}, {background: true});

EOF
