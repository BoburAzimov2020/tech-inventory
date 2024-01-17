import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CameraBrand from './camera-brand';
import CameraBrandDetail from './camera-brand-detail';
import CameraBrandUpdate from './camera-brand-update';
import CameraBrandDeleteDialog from './camera-brand-delete-dialog';

const CameraBrandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CameraBrand />} />
    <Route path="new" element={<CameraBrandUpdate />} />
    <Route path=":id">
      <Route index element={<CameraBrandDetail />} />
      <Route path="edit" element={<CameraBrandUpdate />} />
      <Route path="delete" element={<CameraBrandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CameraBrandRoutes;
