import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cabel from './cabel';
import CabelDetail from './cabel-detail';
import CabelUpdate from './cabel-update';
import CabelDeleteDialog from './cabel-delete-dialog';

const CabelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cabel />} />
    <Route path="new" element={<CabelUpdate />} />
    <Route path=":id">
      <Route index element={<CabelDetail />} />
      <Route path="edit" element={<CabelUpdate />} />
      <Route path="delete" element={<CabelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CabelRoutes;
