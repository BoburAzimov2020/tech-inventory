import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Swich from './swich';
import SwichDetail from './swich-detail';
import SwichUpdate from './swich-update';
import SwichDeleteDialog from './swich-delete-dialog';

const SwichRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Swich />} />
    <Route path="new" element={<SwichUpdate />} />
    <Route path=":id">
      <Route index element={<SwichDetail />} />
      <Route path="edit" element={<SwichUpdate />} />
      <Route path="delete" element={<SwichDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SwichRoutes;
