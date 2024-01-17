import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Avtomat from './avtomat';
import AvtomatDetail from './avtomat-detail';
import AvtomatUpdate from './avtomat-update';
import AvtomatDeleteDialog from './avtomat-delete-dialog';

const AvtomatRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Avtomat />} />
    <Route path="new" element={<AvtomatUpdate />} />
    <Route path=":id">
      <Route index element={<AvtomatDetail />} />
      <Route path="edit" element={<AvtomatUpdate />} />
      <Route path="delete" element={<AvtomatDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AvtomatRoutes;
