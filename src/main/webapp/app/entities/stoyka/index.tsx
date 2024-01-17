import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stoyka from './stoyka';
import StoykaDetail from './stoyka-detail';
import StoykaUpdate from './stoyka-update';
import StoykaDeleteDialog from './stoyka-delete-dialog';

const StoykaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stoyka />} />
    <Route path="new" element={<StoykaUpdate />} />
    <Route path=":id">
      <Route index element={<StoykaDetail />} />
      <Route path="edit" element={<StoykaUpdate />} />
      <Route path="delete" element={<StoykaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StoykaRoutes;
