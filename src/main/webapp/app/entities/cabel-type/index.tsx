import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CabelType from './cabel-type';
import CabelTypeDetail from './cabel-type-detail';
import CabelTypeUpdate from './cabel-type-update';
import CabelTypeDeleteDialog from './cabel-type-delete-dialog';

const CabelTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CabelType />} />
    <Route path="new" element={<CabelTypeUpdate />} />
    <Route path=":id">
      <Route index element={<CabelTypeDetail />} />
      <Route path="edit" element={<CabelTypeUpdate />} />
      <Route path="delete" element={<CabelTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CabelTypeRoutes;
