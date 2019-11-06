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
import { NgbButtonsModule, NgbModalModule, NgbTabsetModule } from '@ng-bootstrap/ng-bootstrap';

import { DatabaseComponent } from './database.component';
import { DatabaseCollectionsComponent } from './collections/database-collections.component';
import { DatabaseCollectionComponent } from './collection/database-collection.component';
import { PipesModule } from '../../pipes/pipes.module';
import { DatabaseSlowQueriesComponent } from './queries/database-slow-queries.component';
import { SpinnerModule } from '../spinner/spinner.module';
import { DatabaseCollectionIndexesComponent } from './collection/database-collection-indexes.component';
import { PaginationModule } from '../paginaton/pagination.module';
import { DatabasesComponent } from './databases.component';
import { DatabaseInfoComponent } from './info/database-info.component';
import { ProfilingStatusModalComponent } from './queries/profiling-status-modal.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    DatabasesComponent,
    DatabaseComponent,
    DatabaseInfoComponent,
    DatabaseCollectionsComponent,
    DatabaseCollectionComponent,
    DatabaseSlowQueriesComponent,
    DatabaseCollectionIndexesComponent,
    ProfilingStatusModalComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([]),
    FormsModule,

    NgbButtonsModule,
    NgbTabsetModule,
    NgbModalModule,

    SpinnerModule,
    PipesModule,
    PaginationModule,
  ],
  providers: [
  ],
  exports: [
    DatabaseComponent,
    DatabasesComponent,
    DatabaseInfoComponent,
  ],
})
export class DatabaseModule {
}
