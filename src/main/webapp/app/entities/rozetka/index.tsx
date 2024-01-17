import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rozetka from './rozetka';
import RozetkaDetail from './rozetka-detail';
import RozetkaUpdate from './rozetka-update';
import RozetkaDeleteDialog from './rozetka-delete-dialog';

const RozetkaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rozetka />} />
    <Route path="new" element={<RozetkaUpdate />} />
    <Route path=":id">
      <Route index element={<RozetkaDetail />} />
      <Route path="edit" element={<RozetkaUpdate />} />
      <Route path="delete" element={<RozetkaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RozetkaRoutes;
