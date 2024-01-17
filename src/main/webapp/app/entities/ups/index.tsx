import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ups from './ups';
import UpsDetail from './ups-detail';
import UpsUpdate from './ups-update';
import UpsDeleteDialog from './ups-delete-dialog';

const UpsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ups />} />
    <Route path="new" element={<UpsUpdate />} />
    <Route path=":id">
      <Route index element={<UpsDetail />} />
      <Route path="edit" element={<UpsUpdate />} />
      <Route path="delete" element={<UpsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UpsRoutes;
