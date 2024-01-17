import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ShelfType from './shelf-type';
import ShelfTypeDetail from './shelf-type-detail';
import ShelfTypeUpdate from './shelf-type-update';
import ShelfTypeDeleteDialog from './shelf-type-delete-dialog';

const ShelfTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ShelfType />} />
    <Route path="new" element={<ShelfTypeUpdate />} />
    <Route path=":id">
      <Route index element={<ShelfTypeDetail />} />
      <Route path="edit" element={<ShelfTypeUpdate />} />
      <Route path="delete" element={<ShelfTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ShelfTypeRoutes;
