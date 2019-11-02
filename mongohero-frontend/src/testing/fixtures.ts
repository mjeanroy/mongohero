/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import { DatabaseModel } from '../app/models/database.model';
import { CollectionModel } from '../app/models/collection.model';
import { ServerModel } from '../app/models/server.model';

export function givenServer(): ServerModel {
  return {
    host: 'localhost',
    version: '3.2.16',
    uptime: 3600,
    connections: {
      current: 10,
      available: 90,
      totalCreated: 20,
    },
    storageEngine: {
      name: 'WiredTiger',
      supportsCommittedReads: true,
      persistent: true,
    },
    databases: [
      givenDatabase(),
    ],
    profilingStatus: {
      level: 1,
      slowMs: 100,
    },
  };
}

export function givenDatabase(): DatabaseModel {
  return {
    name: 'local',
    sizeOnDisk: 5.681262592E9,
    empty: false,
    stats: {
      collections: 223,
      objects: 4830157,
      dataSize: 1.2519473587E10,
      storageSize: 5.018451968E9,
      indexSize: 6.6279424E8,
      indexes: 796,
      numExtents: 0
    },
  };
}

export function givenCollections(): CollectionModel[] {
  return [
    givenAvengersCollection(),
    givenMoviesCollection(),
  ];
}

export function givenAvengersCollection(): CollectionModel {
  return {
    name: 'avengers',
    options: '',
    stats: {
      ns: 'local.avengers',
      size: 4.03046039E8,
      count: 364827,
      avgObjSize: 1104.0,
      storageSize: 1.78429952E8,
      capped: false,
      nindexes: 8,
      totalIndexSize: 4.6014464E7,
      indexSizes: [
        {
          name: '_id_',
          size: 7380992
        },
        {
          name: 'name_1',
          size: 10235904
        },
      ],
    },
  };
}

export function givenMoviesCollection(): CollectionModel {
  return {
    name: 'movies',
    options: '',
    stats: {
      ns: 'local.movies',
      size: 6.5717923E7,
      count: 178157,
      avgObjSize: 368.0,
      storageSize: 1.8644992E7,
      capped: true,
      nindexes: 2,
      totalIndexSize: 7053312.0,
      indexSizes: [
        {
          name: '_id_',
          size: 1921024
        },
        {
          name: 'title_1',
          size: 5132288
        },
      ],
    },
  };
}
