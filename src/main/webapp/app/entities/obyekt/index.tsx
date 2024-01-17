import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Obyekt from './obyekt';
import ObyektDetail from './obyekt-detail';
import ObyektUpdate from './obyekt-update';
import ObyektDeleteDialog from './obyekt-delete-dialog';

const ObyektRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Obyekt />} />
    <Route path="new" element={<ObyektUpdate />} />
    <Route path=":id">
      <Route index element={<ObyektDetail />} />
      <Route path="edit" element={<ObyektUpdate />} />
      <Route path="delete" element={<ObyektDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ObyektRoutes;
