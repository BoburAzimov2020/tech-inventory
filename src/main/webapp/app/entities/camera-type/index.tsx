import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CameraType from './camera-type';
import CameraTypeDetail from './camera-type-detail';
import CameraTypeUpdate from './camera-type-update';
import CameraTypeDeleteDialog from './camera-type-delete-dialog';

const CameraTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CameraType />} />
    <Route path="new" element={<CameraTypeUpdate />} />
    <Route path=":id">
      <Route index element={<CameraTypeDetail />} />
      <Route path="edit" element={<CameraTypeUpdate />} />
      <Route path="delete" element={<CameraTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CameraTypeRoutes;
