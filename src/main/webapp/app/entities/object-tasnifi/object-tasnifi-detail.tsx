import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './object-tasnifi.reducer';

export const ObjectTasnifiDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const objectTasnifiEntity = useAppSelector(state => state.objectTasnifi.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="objectTasnifiDetailsHeading">Object Tasnifi</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{objectTasnifiEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{objectTasnifiEntity.name}</dd>
          <dt>Bject Tasnifi Turi</dt>
          <dd>{objectTasnifiEntity.bjectTasnifiTuri ? objectTasnifiEntity.bjectTasnifiTuri.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/object-tasnifi" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/object-tasnifi/${objectTasnifiEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ObjectTasnifiDetail;
