import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Akumulator from './akumulator';
import AkumulatorDetail from './akumulator-detail';
import AkumulatorUpdate from './akumulator-update';
import AkumulatorDeleteDialog from './akumulator-delete-dialog';

const AkumulatorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Akumulator />} />
    <Route path="new" element={<AkumulatorUpdate />} />
    <Route path=":id">
      <Route index element={<AkumulatorDetail />} />
      <Route path="edit" element={<AkumulatorUpdate />} />
      <Route path="delete" element={<AkumulatorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AkumulatorRoutes;
