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

import _get from 'lodash.get';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CollectionModel } from '../../../models/collection.model';
import { CollectionApiService } from '../../../api/collection.api.service';
import { DatabaseModel } from '../../../models/database.model';
import { IndexModel } from '../../../models/index.model';

@Component({
  selector: 'app-database-collection-indexes',
  templateUrl: './database-collection-indexes.component.html',
  styleUrls: [
    './database-collection-indexes.component.scss',
  ],
})
export class DatabaseCollectionIndexesComponent implements OnInit, OnChanges {

  private collectionApiService: CollectionApiService;

  @Input() database: DatabaseModel;
  @Input() collection: CollectionModel;

  indexes: IndexModel[];
  sortField: string;
  sortOrder: number;

  constructor(collectionApiService: CollectionApiService) {
    this.collectionApiService = collectionApiService;
    this.indexes = null;

    this.sortField = 'access.ops';
    this.sortOrder = -1;
  }

  ngOnInit() {
    this._loadIndexes();
  }

  ngOnChanges(changes: SimpleChanges) {
    if ((changes.database && !changes.database.isFirstChange()) || (changes.collection && !changes.collection.isFirstChange())) {
      this._loadIndexes();
    }
  }

  getAlertLevel(index: IndexModel) {
    const ops = index.accesses.ops;

    if (ops === 0) {
      return 'danger';
    }

    if (ops < 10) {
      return 'warning';
    }

    return 'body';
  }

  sort(sortField) {
    if (this.sortField === sortField) {
      this.sortOrder = this.sortOrder * -1;
    }
    else {
      this.sortField = sortField;
      this.sortOrder = 1;
    }

    this._sort();
  }

  private _loadIndexes() {
    if (this.database && this.collection) {
      const db = this.database.name;
      const name = this.collection.name;
      this.collectionApiService.getIndexes(db, name).then((indexes) => {
        this.indexes = indexes;
        this._sort();
      });
    }
  }

  private _sort() {
    if (!this.indexes) {
      return;
    }

    this.indexes.sort((x, y) => {
      const v1 = _get(x, this.sortField);
      const v2 = _get(y, this.sortField);

      if (v1 === v2) {
        return x.name.localeCompare(y.name);
      }

      return (v1 < v2 ? -1 : 1) * this.sortOrder;
    });
  }
}
