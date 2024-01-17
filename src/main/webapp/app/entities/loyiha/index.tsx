import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Loyiha from './loyiha';
import LoyihaDetail from './loyiha-detail';
import LoyihaUpdate from './loyiha-update';
import LoyihaDeleteDialog from './loyiha-delete-dialog';

const LoyihaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Loyiha />} />
    <Route path="new" element={<LoyihaUpdate />} />
    <Route path=":id">
      <Route index element={<LoyihaDetail />} />
      <Route path="edit" element={<LoyihaUpdate />} />
      <Route path="delete" element={<LoyihaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LoyihaRoutes;
