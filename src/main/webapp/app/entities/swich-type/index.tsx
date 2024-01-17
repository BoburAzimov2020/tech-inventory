import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SwichType from './swich-type';
import SwichTypeDetail from './swich-type-detail';
import SwichTypeUpdate from './swich-type-update';
import SwichTypeDeleteDialog from './swich-type-delete-dialog';

const SwichTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SwichType />} />
    <Route path="new" element={<SwichTypeUpdate />} />
    <Route path=":id">
      <Route index element={<SwichTypeDetail />} />
      <Route path="edit" element={<SwichTypeUpdate />} />
      <Route path="delete" element={<SwichTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SwichTypeRoutes;
