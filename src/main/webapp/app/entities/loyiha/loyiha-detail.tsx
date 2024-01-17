import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './loyiha.reducer';

export const LoyihaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const loyihaEntity = useAppSelector(state => state.loyiha.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loyihaDetailsHeading">Loyiha</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{loyihaEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{loyihaEntity.name}</dd>
          <dt>Object Tasnifi</dt>
          <dd>{loyihaEntity.objectTasnifi ? loyihaEntity.objectTasnifi.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/loyiha" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/loyiha/${loyihaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoyihaDetail;
