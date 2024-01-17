import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StoykaType from './stoyka-type';
import StoykaTypeDetail from './stoyka-type-detail';
import StoykaTypeUpdate from './stoyka-type-update';
import StoykaTypeDeleteDialog from './stoyka-type-delete-dialog';

const StoykaTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StoykaType />} />
    <Route path="new" element={<StoykaTypeUpdate />} />
    <Route path=":id">
      <Route index element={<StoykaTypeDetail />} />
      <Route path="edit" element={<StoykaTypeUpdate />} />
      <Route path="delete" element={<StoykaTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StoykaTypeRoutes;
