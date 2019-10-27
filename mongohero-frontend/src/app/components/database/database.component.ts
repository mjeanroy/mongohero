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

import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatabaseApiService } from '../../api/database.api.service';
import { CollectionApiService } from '../../api/collection.api.service';
import { DatabaseModel } from '../../models/database.model';
import { CollectionModel } from '../../models/collection.model';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: [
    './database.component.scss',
  ],
})
export class DatabaseComponent implements OnInit {

  private route: ActivatedRoute;
  private databaseApiService: DatabaseApiService;
  private collectionApiService: CollectionApiService;

  filter: string;
  database: DatabaseModel;
  collections: CollectionModel[];

  constructor(
    route: ActivatedRoute,
    databaseApiService: DatabaseApiService,
    collectionApiService: CollectionApiService) {

    this.route = route;
    this.databaseApiService = databaseApiService;
    this.collectionApiService = collectionApiService;

    this.database = null;
    this.collections = null;
    this.filter = '';
  }

  ngOnInit() {
    this.route.params.subscribe(
      (params) => this._load(params.database)
    );
  }

  private _load(db: string) {
    this._loadDatabase(db);
    this._loadCollections(db);
  }

  private _loadDatabase(db: string) {
    this.databaseApiService.get(db).then((database) => (
      this.database = database
    ));
  }

  private _loadCollections(db: string) {
    this.collectionApiService.getAll(db)
      .then((collections) => (
        this.collections = collections
      ));
  }
}
