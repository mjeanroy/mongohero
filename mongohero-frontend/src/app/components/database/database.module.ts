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

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DatabaseComponent } from './database.component';
import { NgbButtonsModule, NgbTabsetModule } from '@ng-bootstrap/ng-bootstrap';
import { DatabaseInfoComponent } from './info/database-info.component';
import { DatabaseCollectionsComponent } from './collections/database-collections.component';
import { DatabaseCollectionComponent } from './collection/database-collection.component';
import { PipesModule } from '../../pipes/pipes.module';
import { DatabaseSlowQueriesComponent } from './queries/database-slow-queries.component';
import { SpinnerModule } from '../spinner/spinner.module';
import { DatabaseCollectionIndexesComponent } from './collection/database-collection-indexes.component';
import { PaginationModule } from '../paginaton/pagination.module';

@NgModule({
  declarations: [
    DatabaseComponent,
    DatabaseInfoComponent,
    DatabaseCollectionsComponent,
    DatabaseCollectionComponent,
    DatabaseSlowQueriesComponent,
    DatabaseCollectionIndexesComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([]),

    NgbButtonsModule,
    NgbTabsetModule,

    SpinnerModule,
    PipesModule,
    PaginationModule,
  ],
  providers: [
  ],
  exports: [
    DatabaseComponent,
  ],
})
export class DatabaseModule {
}
