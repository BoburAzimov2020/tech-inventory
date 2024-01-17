import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './object-tasnifi-turi.reducer';

export const ObjectTasnifiTuriDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const objectTasnifiTuriEntity = useAppSelector(state => state.objectTasnifiTuri.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="objectTasnifiTuriDetailsHeading">Object Tasnifi Turi</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{objectTasnifiTuriEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{objectTasnifiTuriEntity.name}</dd>
          <dt>District</dt>
          <dd>{objectTasnifiTuriEntity.district ? objectTasnifiTuriEntity.district.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/object-tasnifi-turi" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/object-tasnifi-turi/${objectTasnifiTuriEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ObjectTasnifiTuriDetail;
