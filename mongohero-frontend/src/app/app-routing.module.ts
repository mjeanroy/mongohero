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

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DashboardModule } from './components/dashboard/dashboard.module';
import { DatabaseComponent } from './components/database/database.component';
import { DatabaseModule } from './components/database/database.module';
import { DatabasesComponent } from './components/database/databases.component';
import { ServerModule } from './components/server/server.module';
import { ServerComponent } from './components/server/server.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },

  {
    path: 'dashboard',
    component: DashboardComponent,
  },

  {
    path: 'databases',
    component: DatabasesComponent,
  },

  {
    path: 'databases/:database',
    redirectTo: 'databases/:database/info'
  },

  {
    path: 'databases/:database/:view',
    component: DatabaseComponent,
  },

  {
    path: 'server',
    redirectTo: 'server/operations'
  },

  {
    path: 'server/:view',
    component: ServerComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),

    DashboardModule,
    DatabaseModule,
    ServerModule,
  ],
  exports: [
    RouterModule,
  ],
})
export class AppRoutingModule {

}
