import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './buyurtma-raqam.reducer';

export const BuyurtmaRaqamDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buyurtmaRaqamEntity = useAppSelector(state => state.buyurtmaRaqam.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buyurtmaRaqamDetailsHeading">Buyurtma Raqam</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{buyurtmaRaqamEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{buyurtmaRaqamEntity.name}</dd>
          <dt>
            <span id="numberOfOrder">Number Of Order</span>
          </dt>
          <dd>{buyurtmaRaqamEntity.numberOfOrder}</dd>
          <dt>Loyiha</dt>
          <dd>{buyurtmaRaqamEntity.loyiha ? buyurtmaRaqamEntity.loyiha.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/buyurtma-raqam" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/buyurtma-raqam/${buyurtmaRaqamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuyurtmaRaqamDetail;
