import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Shelf from './shelf';
import ShelfDetail from './shelf-detail';
import ShelfUpdate from './shelf-update';
import ShelfDeleteDialog from './shelf-delete-dialog';

const ShelfRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Shelf />} />
    <Route path="new" element={<ShelfUpdate />} />
    <Route path=":id">
      <Route index element={<ShelfDetail />} />
      <Route path="edit" element={<ShelfUpdate />} />
      <Route path="delete" element={<ShelfDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ShelfRoutes;
