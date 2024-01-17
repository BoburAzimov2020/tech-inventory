import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuyurtmaRaqam from './buyurtma-raqam';
import BuyurtmaRaqamDetail from './buyurtma-raqam-detail';
import BuyurtmaRaqamUpdate from './buyurtma-raqam-update';
import BuyurtmaRaqamDeleteDialog from './buyurtma-raqam-delete-dialog';

const BuyurtmaRaqamRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BuyurtmaRaqam />} />
    <Route path="new" element={<BuyurtmaRaqamUpdate />} />
    <Route path=":id">
      <Route index element={<BuyurtmaRaqamDetail />} />
      <Route path="edit" element={<BuyurtmaRaqamUpdate />} />
      <Route path="delete" element={<BuyurtmaRaqamDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuyurtmaRaqamRoutes;
