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

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { DatabaseApiService } from '../../api/database.api.service';
import { CollectionApiService } from '../../api/collection.api.service';
import { DatabaseModel } from '../../models/database.model';
import { CollectionModel } from '../../models/collection.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: [
    './database.component.scss',
  ],
})
export class DatabaseComponent implements OnInit, OnDestroy {

  private route: ActivatedRoute;
  private router: Router;
  private databaseApiService: DatabaseApiService;
  private collectionApiService: CollectionApiService;

  private routeParamsSubscription: Subscription;
  private db: string;

  filter: string;
  database: DatabaseModel;
  collections: CollectionModel[];
  selectedTab: string;

  constructor(
    route: ActivatedRoute,
    router: Router,
    databaseApiService: DatabaseApiService,
    collectionApiService: CollectionApiService) {

    this.route = route;
    this.router = router;
    this.databaseApiService = databaseApiService;
    this.collectionApiService = collectionApiService;

    this.database = null;
    this.collections = null;
    this.filter = '';
  }

  ngOnInit() {
    this.routeParamsSubscription = this.route.params.subscribe((params) => (
      this._onRouteParamsUpdated(params)
    ));

    const routeSnapshot = this.route.snapshot;
    const currentParams = routeSnapshot.params || {};
    const initialView = currentParams.view || 'info';
    this._initializeSelectedTab(initialView);
  }

  ngOnDestroy() {
    if (this.routeParamsSubscription) {
      this.routeParamsSubscription.unsubscribe();
    }
  }

  onTabChange(id) {
    this.router.navigate(['/databases', this.database.name, id]);
  }

  private _initializeSelectedTab(tab: string) {
    this.selectedTab = tab;
  }

  private _onRouteParamsUpdated(params: Params) {
    this._load(params.database);
  }

  private _load(db: string) {
    if (this.db !== db) {
      this.db = db;

      this._loadDatabase();
      this._loadCollections();
    }
  }

  private _loadDatabase() {
    this.databaseApiService.get(this.db).then((database) => (
      this.database = database
    ));
  }

  private _loadCollections() {
    this.collectionApiService.getAll(this.db)
      .then((collections) => (
        this.collections = collections
      ));
  }
}
