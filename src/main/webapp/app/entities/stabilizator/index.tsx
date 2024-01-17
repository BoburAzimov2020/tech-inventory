import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stabilizator from './stabilizator';
import StabilizatorDetail from './stabilizator-detail';
import StabilizatorUpdate from './stabilizator-update';
import StabilizatorDeleteDialog from './stabilizator-delete-dialog';

const StabilizatorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stabilizator />} />
    <Route path="new" element={<StabilizatorUpdate />} />
    <Route path=":id">
      <Route index element={<StabilizatorDetail />} />
      <Route path="edit" element={<StabilizatorUpdate />} />
      <Route path="delete" element={<StabilizatorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StabilizatorRoutes;
